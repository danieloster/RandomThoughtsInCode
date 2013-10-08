package experiment.util.collections;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.List;
import java.util.Map;

public interface Selectable<T> {
    List<T> toList();

    <U> Selectable<U> select(Function<T, U> trans);

    Selectable<T> apply(Function<T, Void> action);

    Selectable<T> where(Predicate<T> predicate);

    <U> Map<U, T> createMap(Function<T, U> key);

    T first();
}
