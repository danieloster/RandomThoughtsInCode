package experiment.pipeit.pipes;

import experiment.pipeit.Pipe;
import experiment.pipeit.exceptions.NoMoreValues;
import com.google.common.base.Predicate;

public class Gate<T> {

        private BranchingPipe<T> pipe;
        private Predicate<T> condition;
        private Pipe<T, T> path;

        public Gate(BranchingPipe<T> pipe, Predicate<T> condition) {
            this.pipe = pipe;
            this.condition = condition;
        }

        public BranchingPipe<T> doo(Pipe<T, T> path) {
            this.path = path;
            return pipe;
        }

        public boolean isOpenFor(T item) {
            return condition.apply(item);
        }

        public T pull() throws NoMoreValues {
            return path.pull();
        }

        public void push(T item) {
            path.push(item);
        }

    public BranchingPipe<T> getPipe() {
        return pipe;
    }
}
