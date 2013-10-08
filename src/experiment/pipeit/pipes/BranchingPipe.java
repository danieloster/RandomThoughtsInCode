package experiment.pipeit.pipes;

import experiment.pipeit.Pipe;
import experiment.pipeit.PipeImpl;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.LinkedList;
import java.util.List;

import static experiment.util.collections.Collections.from;

public class BranchingPipe<T> extends PipeImpl<T, T> implements PipeCollection<T>{

    private List<Gate<T>> gates = new LinkedList<Gate<T>>();

    public Gate when(Predicate<T> condition) {
        Gate<T> gate = new Gate<T>(this, condition);
        gates.add(gate);
        return gate;
    }

    @Override
    public void push(T item) {
        Gate<T> gate = from(gates).where(gateIsOpenFor(item)).first();
        if(gate != null) {
            gate.push(item);
        }
    }

    @Override
    public T apply(T input) {
        return input;
    }



    @Override
    public List<Pipe<T, T>> getOutputPipes() {
        return from(gates).select(pipes()).toList();
    }

    private Function<Gate<T>,Pipe<T, T>> pipes() {
        return new Function<Gate<T>, Pipe<T, T>>() {
            @Override
            public Pipe<T, T> apply(Gate<T> gate) {
                return  gate.getPipe();
            }
        };
    }

    private Predicate<Gate<T>> gateIsOpenFor(final T item) {
        return new Predicate<Gate<T>>() {
            @Override
            public boolean apply(Gate<T> gate) {
                return gate.isOpenFor(item);
            }
        };
    }
}
