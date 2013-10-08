package experiment.pipeit;

import experiment.pipeit.exceptions.NoMoreValues;

public class Glue<T> implements Input<T>, Output<T> {
    private Pipe<?, T> upstream;
    private Pipe<T, ?> downstream;

    public Glue(Pipe<?, T> upstream, Pipe<T, ?> downstream) {
        this.upstream = upstream;
        this.downstream = downstream;
        upstream.setOutput(this);
        downstream.setInput(this);
    }

    public static <T> Glue newGlueBetween(Pipe<?, T> upstream, Pipe<T, ?> downstream) {
        return new Glue(upstream, downstream);
    }

    @Override
    public T pull() throws NoMoreValues {
        return upstream.pull();
    }

    @Override
    public void push(T value) {
        downstream.push(value);
    }
}
