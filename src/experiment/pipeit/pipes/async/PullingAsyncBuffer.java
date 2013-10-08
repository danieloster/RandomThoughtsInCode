package experiment.pipeit.pipes.async;

import experiment.pipeit.Input;
import experiment.pipeit.exceptions.NoMoreValues;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import java.util.concurrent.ArrayBlockingQueue;

public class PullingAsyncBuffer<T>  {
    private ArrayBlockingQueue<Optional<T>> buffer;
    private Input<T> input;
    private PollingService poller;

    public PullingAsyncBuffer(int capacity, Input<T> input) {
        this.input = input;
        buffer = new ArrayBlockingQueue<Optional<T>>(capacity);

    }

    public PullingAsyncBuffer<T> start() {
        if(poller == null) {
            poller = new PollingService();
            poller.startAsync();
        }
        return this;
    }

    public PullingAsyncBuffer<T> stop() {
        if(poller != null) {
            poller.stopAsync();
            poller.awaitTerminated();
            poller = null;
        }
        return this;
    }

    public T get() throws NoMoreValues {
        Optional<T> v;
        if(poller != null && poller.isRunning()) {
            v = takeValue();
        } else {
            stop();
            start();
            v = takeValue();

        }
        if(!v.isPresent()) {
            throw new NoMoreValues();
        }
        return v.get();
    }

    private Optional<T> takeValue() {
        Optional<T> v = null;
        try {
            v = buffer.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return v;
    }

    private class PollingService extends AbstractExecutionThreadService {
    @Override
    protected void run() throws Exception {
        while (isRunning()) {

            try {
                T v;
                do {
                    v = input.pull();
                } while (v == null);
                if(v != null) {
                    buffer.put(Optional.of(v));
                }
            } catch (NoMoreValues noMoreValues) {
                triggerShutdown();
                buffer.put(Optional.<T>absent());
            }
        }
    }
    }
}
