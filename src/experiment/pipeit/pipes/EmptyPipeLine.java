package experiment.pipeit.pipes;

import experiment.pipeit.Pipe;
import experiment.pipeit.Pipeline;

public class EmptyPipeLine  {

    public <I, U> Pipeline<I, U> startWith(Pipe<I, U> next) {
        return new Pipeline<I, U>(next, next);
    }
}
