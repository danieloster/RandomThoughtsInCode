package experiment.util.collections.implementation;

import experiment.util.collections.Selectable;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.*;

public class SelectableStandrardImpl<T> implements Selectable<T> {
    private Collection<T> collection;

    public SelectableStandrardImpl(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public List<T> toList() {
        return new LinkedList<T>(collection);
    }

    @Override
    public <U> Selectable<U> select(Function<T, U> trans) {
        List<U> list = new LinkedList<U>();
        for (T item : collection) {
            list.add(trans.apply(item));
        }
        return new SelectableStandrardImpl<U>(list);
    }

    @Override
    public Selectable<T> apply(Function<T, Void> action) {
        for (T item : collection) {
            action.apply(item);
        }
        return this;
    }

    @Override
    public Selectable<T> where(Predicate<T> predicate) {
        List<T> list = new LinkedList<T>();
        for (T item : collection) {
            if (predicate.apply(item)) {
                list.add(item);
            }
        }
        return new SelectableStandrardImpl<T>(list);
    }

    @Override
    public <U> Map<U, T> createMap(Function<T, U> key) {
        Map<U, T> map = new HashMap<U, T>(collection.size());
        apply(addToMap(key, map));
        return map;
    }

    @Override
    public T first() {
        for(T item : collection) {
            return item;
        }
        return null;
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
