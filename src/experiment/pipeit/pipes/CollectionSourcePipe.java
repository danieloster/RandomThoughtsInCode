package experiment.pipeit.pipes;

import experiment.pipeit.PipeImpl;
import experiment.pipeit.exceptions.NoMoreValues;

import java.util.Collection;
import java.util.Iterator;

class CollectionSourcePipe<T> extends PipeImpl<Void, T> {
    private Iterator<T> iterator;
    private final Collection<T> collection;

    public CollectionSourcePipe(Collection<T> collection) {
        this.collection = collection;
        iterator = collection.iterator();
    }

    @Override
    public T pull() throws NoMoreValues {
        T value = apply(null);
        if (value == null) {
            iterator = collection.iterator();
            throw new NoMoreValues();
        }
        return value;
    }


    @Override
    public T apply(Void input) {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
