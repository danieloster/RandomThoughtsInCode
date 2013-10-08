package experiment.util.collections.implementation;

import experiment.util.collections.Collections;
import experiment.util.collections.Selectable;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class SelectablePipelineImplTest {
    private Selectable<Integer> start;

    @Before
    public void setUp() throws Exception {
        start = Collections.pipeFrom(Arrays.asList(1, 2, 3, 45, 6, 8));
    }

    @Test
    public void testSelect() throws Exception {
        Collection<Integer> integers = start.toList();
        assertThat(integers, hasItems(1, 2, 3, 45, 6, 8));

    }

    @Test
    public void testApply() throws Exception {
        final List<Integer> l = new LinkedList<Integer>();
        start.apply(new Function<Integer, Void>() {
            @Override
            public Void apply(Integer integer) {
                l.add(integer);
                return null;
            }
        });

        Collection<Integer> integers = start.toList();
        assertThat(integers, hasItems(1, 2, 3, 45, 6, 8));
        assertThat(l, hasItems(1, 2, 3, 45, 6, 8));

    }

    @Test
    public void testWhere() throws Exception {
        Selectable<Integer> res = start.where(numberIsOdd());
        assertThat(res.toList(), hasItems(1, 3, 45));
    }

    @Test
    public void testCreateMap() throws Exception {
        Map<Integer, Integer> map = start.createMap(key());

        for (int i : Arrays.asList(1, 2, 3, 45, 6, 8)) {
            assertThat(map.get(i), is(i));
        }
    }


    private Predicate<Integer> numberIsOdd() {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(java.lang.Integer integer) {
                return integer % 2 == 1;
            }
        };
    }

    private Function<Integer, Integer> key() {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer;
            }
        };
    }
}
