package experiment.pipeit.pipes;

import experiment.pipeit.Pipe;
import experiment.pipeit.PipeImpl;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.Collection;

public abstract class Pipes {
    public static <T> Pipe<Void, T> source(Collection<T> collection) {
        return new CollectionSourcePipe(collection);
    }

    public static <VT> Pipe<VT, VT> where(final Predicate<VT> pred) {
        return new PipeImpl<VT, VT>() {
            @Override
            public VT apply(VT input) {
                if (pred.apply(input)) {
                    return input;
                }
                return null;
            }
        };
    }

    public static <I, O> Pipe<I, O> transform(final Function<I, O> transformer) {
        return new PipeImpl<I, O>() {
            @Override
            public O apply(I input) {
                return transformer.apply(input);
            }
        };
    }

    public static <I> Pipe<I, I> sideEffect(final Function<I, Void> function) {
        return new PipeImpl<I, I>() {
            @Override
            public I apply(I input) {
                function.apply(input);
                return input;
            }
        };
    }

    public static <T> Pipe<T, T> join(Pipe<?,T> ... pipes) {
        return new JoiningPipe<T>(pipes);
    }

    public static <T> Pipe<T, T> join(Pipe<?,T> pipe) {
        if(pipe instanceof PipeCollection) {
            return new JoiningPipe<T>(((PipeCollection)pipe).getOutputPipes());
        }
        throw new IllegalArgumentException("Pipe must implement PipeCollection");

    }

    public static <T> BufferedAsyncPipe<T> prefetch() {
        return new BufferedAsyncPipe<T>();
    }

    public static <T> BranchingPipe<T> branch() {
        return new BranchingPipe<T>();

    }
    private Pipes() {

    }

}
