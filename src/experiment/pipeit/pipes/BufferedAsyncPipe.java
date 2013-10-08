package experiment.pipeit.pipes;

import experiment.pipeit.Input;
import experiment.pipeit.PipeImpl;
import experiment.pipeit.exceptions.NoMoreValues;
import experiment.pipeit.pipes.async.PullingAsyncBuffer;

public class BufferedAsyncPipe<T> extends PipeImpl<T, T> {

    private PullingAsyncBuffer<T> pullingBuffer;
    private int capacity = 15;

    public BufferedAsyncPipe<T> start() {
        if(pullingBuffer == null) {
            throw new IllegalStateException("input is not set yet");
        }
        pullingBuffer.start();
        return this;
    }

    public BufferedAsyncPipe<T> withCapacity(int capacity) {
        if(pullingBuffer != null) {
            throw new IllegalStateException("buffer already created");
        }
        this.capacity = capacity;
        return this;
    }

    @Override
    public void setInput(Input<T> input) {
        pullingBuffer = new PullingAsyncBuffer<T>(capacity, input);
        super.setInput(input);
    }

    @Override
    public T pull() throws NoMoreValues {
        return pullingBuffer.get();
    }

    @Override
    public T apply(T input) {
       return input;
    }
}
