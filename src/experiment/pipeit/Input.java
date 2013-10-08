package experiment.pipeit;

import experiment.pipeit.exceptions.NoMoreValues;

public interface Input<I> {

    I pull() throws NoMoreValues;
}
