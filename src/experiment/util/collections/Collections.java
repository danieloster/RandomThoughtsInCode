package experiment.util.collections;

import experiment.util.collections.implementation.SelectablePipelineImpl;
import experiment.util.collections.implementation.SelectableStandrardImpl;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 2013-10-04
 * Time: 19.38
 * To change this template use File | Settings | File Templates.
 */
public abstract class Collections {

    public static <T> Selectable<T> from(Collection<T> collection) {
        return new SelectableStandrardImpl<T>(collection);
    }

    public static <T> Selectable<T> from(T[] array) {
        return from(asList(array));
    }

    public static <T> Selectable<T> pipeFrom(Collection<T> collection) {
        return new SelectablePipelineImpl<T>(collection);
    }
}
