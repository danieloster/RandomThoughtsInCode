package experiment.pipeit;

import experiment.pipeit.pipes.Pipes;
import com.google.common.base.Predicate;
import org.junit.Test;

import java.util.Arrays;

import static experiment.pipeit.Pipeline.createPipeline;
import static experiment.pipeit.pipes.Pipes.*;
import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class Test_Pipeline {

    @Test
    public void main() {
        createPipeline()
                .startWith(source(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)))
                .concat(where(numberIsOdd()))
                .concat(assertThatNumberIdOdd())
                .collect();
    }

    @Test
    public void pipeOutAndJoin() {
        Pipeline<Void, Integer> pipeline = createPipeline()
                                .startWith(source(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));

        Pipeline<Void, Integer> odd = pipeline.concat(where(numberIsOdd()));
        odd.add(multiply(2));

        Pipeline<Void, Integer> even = pipeline.concat(where(not(numberIsOdd())));
        even.add(multiply(-1));

        assertThat(odd.collect(), hasItems(2,6,10,14));
        assertThat(even.collect(), hasItems(-2, -4, -6, -8));

        Pipeline<Integer, Integer> joined = createPipeline().startWith(join(odd, even));
        assertThat(joined.collect(), hasItems(-2, -4, -6, -8, 2, 6, 10, 14));


    }

    @Test
    public void branchOutAndJoin() {
        Pipeline<Integer, Integer> pipeline = createPipeline()
                .startWith(multiply(2))
                .add(Pipes.<Integer>branch()
                        .when(numberIsOdd()).doo(multiply(-1))
                        .when(not(numberIsOdd())).doo(multiply(10)));

                pipeline.add(join(pipeline.tail()))
                .add(assertSequence(1, 4, 6));

        pipeline.push(3);
        pipeline.push(3);
        pipeline.push(3);

    }

    private PipeImpl<Integer, Integer> assertSequence(final int ... seq) {
        return new PipeImpl<Integer, Integer>() {
           private int i;
            @Override
            public Integer apply(Integer input) {
                assertThat(input, is(seq[i++]));
                return input;
            }
        };
    }


    @Test
    public void asyncPull() {
        Pipeline<Void, Integer> pipeline = createPipeline()
                .startWith(source(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)))
                .add(where(numberIsOdd()))
                .add(Pipes.<Integer>prefetch().withCapacity(2))
                .add(multiply(2));

        assertThat(pipeline.collect(), hasItems(2,6,10,14));



    }



    private PipeImpl<Integer, Integer> multiply(final int mul) {
        return new PipeImpl<Integer, Integer>() {
            @Override
            public Integer apply(Integer i) {
                return i * mul;
            }
        };
    }


    private Pipe<Integer, Integer> assertThatNumberIdOdd() {
        return new PipeImpl<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                assertThat(input % 2, is(1));
                return input;
            }
        };
    }

    private Predicate<Integer> numberIsOdd() {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(java.lang.Integer integer) {
                return integer % 2 == 1;
            }
        };
    }
}
