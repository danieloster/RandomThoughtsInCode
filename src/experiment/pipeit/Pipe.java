package experiment.pipeit;

import experiment.pipeit.exceptions.NoMoreValues;

public interface Pipe<I, O> {
    void setInput(Input<I> input);

    void setOutput(Output<O> output);

    O pull() throws NoMoreValues;

    void push(I inputValue);

    O apply(I input);
}
