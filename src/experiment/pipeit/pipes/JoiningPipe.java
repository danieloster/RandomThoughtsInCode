package experiment.pipeit.pipes;

import experiment.pipeit.Input;
import experiment.pipeit.Pipe;
import experiment.pipeit.PipeImpl;
import experiment.pipeit.exceptions.NoMoreValues;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;

import static experiment.pipeit.Glue.newGlueBetween;
import static experiment.util.collections.Collections.from;
public class JoiningPipe<T> extends PipeImpl<T, T> {

    private final List<Input<T>> inputs = new ArrayList<Input<T>>();
    private int nextInputToPull;

    public JoiningPipe(Pipe<?, T>[] pipes) {
        from(pipes).apply(glueMeIn(this)).toList();
    }

    public JoiningPipe(List<Pipe<?, T>> pipes) {
        this(pipes.toArray(new Pipe[0]));
    }

    @Override
    public void setInput(Input<T> input) {
       inputs.add(input);
    }

    @Override
    public T pull() throws NoMoreValues {
        if (inputs.isEmpty()) {
            throw new NoMoreValues();
        }
        T outputValue;
        Input<T> input = inputs.get(nextInputToPull++ % inputs.size());

        do {
            outputValue = apply(input.pull());
        } while (outputValue == null);
        return outputValue;
    }

    @Override
    public T apply(T input) {
        return input;
    }


    private static <U> Function<Pipe<?, U>, Void> glueMeIn(final JoiningPipe<U> me) {
        return new Function<Pipe<?, U>, Void>() {
            @Override
            public Void apply(Pipe<?, U> pipe) {
                 newGlueBetween(pipe, me);
                return null;
                }
        };
    }
}
