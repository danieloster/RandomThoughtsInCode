package experiment.pipeit;

import experiment.pipeit.exceptions.NoMoreValues;
import experiment.pipeit.pipes.EmptyPipeLine;

import java.util.LinkedList;
import java.util.List;

import static experiment.pipeit.Glue.newGlueBetween;

public class Pipeline<I, U>  extends PipeImpl<I, U> {

    private Pipe<I, ?> head;
    private Pipe<?, U> tail;

    public Pipeline(Pipe<I, ?> head, Pipe<?, U> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static EmptyPipeLine createPipeline() {
        return new EmptyPipeLine();
    }

    public Pipe<?, U> tail() {
        return tail;
    }

    public <NU> Pipeline<I, NU> concat(Pipe<U, NU> next) {
        newGlueBetween(tail, next);
        return new Pipeline<I, NU>(head, next);
    }


    public Pipeline<I, U> add(Pipe<U, U> next) {
        newGlueBetween(tail, next);
        tail = next;
        return this;
    }

    @Override
    public void setInput(Input<I> input) {
        head.setInput(input);
    }

    @Override
    public void setOutput(Output<U> output) {
        tail.setOutput(output);
    }

    public U pull() throws NoMoreValues {
        return tail.pull();
    }


    public void push(I inputValue) {
        head.push(inputValue);
    }

    public List<U> collect() {
        List<U> list = new LinkedList<U>();
        U item;
        try {
            while ((item = tail.pull()) != null) {
                list.add(item);
            }
        } catch (NoMoreValues noMoreValues) {
            return list;
        }
        return list;
    }

    @Override
    public U apply(I input) {
        try {
            return tail.pull();
        } catch (NoMoreValues noMoreValues) {
            return null;
        }
    }
}

