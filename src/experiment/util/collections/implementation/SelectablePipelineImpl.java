package experiment.util.collections.implementation;

import experiment.pipeit.Pipe;
import experiment.pipeit.Pipeline;
import experiment.pipeit.exceptions.NoMoreValues;
import experiment.pipeit.pipes.Pipes;
import experiment.util.collections.Selectable;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static experiment.pipeit.Pipeline.createPipeline;
import static experiment.pipeit.pipes.Pipes.*;

public class SelectablePipelineImpl<T> implements Selectable<T> {
    private Pipeline<Void, T> pipeline;

    public SelectablePipelineImpl(Collection<T> collection) {
        pipeline = createPipeline().startWith(source(collection));
    }

    private SelectablePipelineImpl(Pipeline<Void, T> pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public List<T> toList() {
        return pipeline.collect();
    }

    @Override
    public <U> Selectable<U> select(Function<T, U> func) {
        return addPipe(transform(func));
    }

    private <U> Selectable<U> addPipe(Pipe<T, U> pipe) {
        return new SelectablePipelineImpl<U>(this.pipeline.concat(pipe));
    }

    @Override
    public Selectable<T> apply(Function<T, Void> action) {
        pipeline = pipeline.concat(sideEffect(action));
        return this;

    }

    @Override
    public Selectable<T> where(Predicate<T> predicate) {
        pipeline = pipeline.concat(Pipes.where(predicate));
        return this;
    }

    @Override
    public <U> Map<U, T> createMap(Function<T, U> key) {
        Map<U, T> map = new HashMap<U, T>();
        Pipeline<Void, T> p = pipeline.concat(sideEffect(addToMap(key, map)));
        p.collect();
        return map;
    }

    @Override
    public T first() {
        try {
            return pipeline.pull();
        } catch (NoMoreValues noMoreValues) {
            return null;
        }
    }

    private <U> Function<T, Void> addToMap(final Function<T, U> key, final Map<U, T> map) {
        return new Function<T, Void>() {
            @Override
            public Void apply(T t) {
                map.put(key.apply(t), t);
                return null;
            }
        };
    }
}
