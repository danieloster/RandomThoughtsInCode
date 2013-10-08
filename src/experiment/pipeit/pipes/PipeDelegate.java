package experiment.pipeit.pipes;

import experiment.pipeit.Input;
import experiment.pipeit.Output;
import experiment.pipeit.Pipe;
import experiment.pipeit.exceptions.NoMoreValues;

public class PipeDelegate<I, O> implements Pipe<I,O> {

    private Input<I> input;
    private Output<O> output;
    private Pipe<I, O> masterPipe;

    public PipeDelegate(Pipe<I, O> masterPipe) {

        this.masterPipe = masterPipe;
    }

    @Override
    public void setInput(Input<I> input) {
        this.input = input;
    }

    @Override
    public void setOutput(Output<O> output) {
        this.output = output;
    }

    @Override
    public O pull() throws NoMoreValues {
        if (input == null) {
            throw new NoMoreValues();
        }
        O outputValue;
        do {
            outputValue = apply(input.pull());
        } while (outputValue == null);
        return outputValue;
    }


    @Override
    public void push(I inputValue) {
        if (output == null) {
            return;
        }
        O outputValue = apply(inputValue);
        if (outputValue != null) {
            output.push(outputValue);
        }
    }

    @Override
    public O apply(I input) {
        return masterPipe.apply(input);
    }


}
