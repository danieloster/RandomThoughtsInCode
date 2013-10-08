package experiment.pipeit;

import experiment.pipeit.exceptions.NoMoreValues;
import experiment.pipeit.pipes.PipeDelegate;

public abstract class PipeImpl<I, O> implements Pipe<I,O> {

    private PipeDelegate<I, O> pipeInfrastrucure;

    protected PipeImpl() {
        this.pipeInfrastrucure = new PipeDelegate<I, O>(this);
    }

    @Override
    public void setInput(Input<I> input) {
        pipeInfrastrucure.setInput(input);
    }

    @Override
    public void setOutput(Output<O> output) {
        pipeInfrastrucure.setOutput(output);
    }

    @Override
    public O pull() throws NoMoreValues {
        return pipeInfrastrucure.pull();
    }


    @Override
    public void push(I inputValue) {
       pipeInfrastrucure.push(inputValue);
    }

}
