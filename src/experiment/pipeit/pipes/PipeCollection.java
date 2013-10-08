package experiment.pipeit.pipes;

import experiment.pipeit.Pipe;

import java.util.List;

public interface PipeCollection<T> {

    public List<Pipe<T, T>> getOutputPipes();
}
