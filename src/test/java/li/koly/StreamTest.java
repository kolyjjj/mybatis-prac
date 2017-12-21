package li.koly;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamTest {


    @Test
    public void should_filter() {
        List<Integer> list = prepareList();
        List<Integer> result = list.stream().filter(i -> i > 2).collect(Collectors.toList());
        assertThat(result).containsExactly(4, 3);
    }

    private List<Integer> prepareList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(3);
        return list;
    }

    @Test
    public void should_reduce_to_sum() {
        List<Integer> list = prepareList();
        Optional<Integer> sum = list.stream().reduce((i1, i2) -> {
            System.out.println(i1 + ", " + i2);
            return i1 + i2;
        });
        assertThat(sum.get()).isEqualTo(10);
    }

    @Test
    public void should_reduce_to_concat() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        Optional<String> concat = list.stream().reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s1, String s2) {
                return s1 + ", " + s2;
            }
        });
        assertThat(concat.get()).isEqualTo("one, two, three");
    }

    @Test
    public void should_reduce_to_min() {
        List<Integer> list = prepareList();
        Optional<Integer> min = list.stream().reduce(BinaryOperator.minBy(Integer::compareTo));
        assertThat(min.get()).isEqualTo(1);
    }

    @Test
    public void should_reduce_to_max() {
        List<Integer> list = prepareList();
        Optional<Integer> max = list.stream().reduce(BinaryOperator.maxBy(Integer::compareTo));
        assertThat(max.get()).isEqualTo(4);
    }

    @Test
    public void should_reduce_to_max_manually() {
        List<Integer> list = prepareList();
        Optional<Integer> max = list.stream().reduce((i1, i2) -> i1 > i2 ? i1 : i2);
        assertThat(max.get()).isEqualTo(4);
    }

    @Test
    public void should_collect_to_list() {
        List<Integer> list = prepareList();
        list.stream().collect(new Collector<Integer, Integer, Integer>() {
            @Override
            public Supplier<Integer> supplier() {
                return null;
            }

            @Override
            public BiConsumer<Integer, Integer> accumulator() {
                return null;
            }

            @Override
            public BinaryOperator<Integer> combiner() {
                return null;
            }

            @Override
            public Function<Integer, Integer> finisher() {
                return null;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return null;
            }
        });
    }

    @Test
    public void should_map() {

    }

    @Test
    public void should_flat_map() {

    }


    @Test
    public void should_distinct() {

    }

    @Test
    public void should_sorted() {

    }

    @Test
    public void should_peek() {

    }

    @Test
    public void should_limit() {

    }


    @Test
    public void should_skip() {

    }


    @Test
    public void should_foreach() {

    }


    @Test
    public void should_foreach_ordered() {

    }


    @Test
    public void should_max() {

    }


    @Test
    public void should_count() {

    }


    @Test
    public void should_any_match() {

    }


    @Test
    public void should_all_match() {

    }


    @Test
    public void should_non_match() {

    }


    @Test
    public void should_find_first() {

    }


    @Test
    public void should_find_any() {

    }


    @Test
    public void should_empty() {

    }


    @Test
    public void should_of() {

    }


    @Test
    public void should_iterate() {

    }


    @Test
    public void should_generate() {

    }


    @Test
    public void should_concat() {

    }


    @Test
    public void should_use_stream_build() {
        Stream.Builder<Integer> builder = new Stream.Builder<>();

    }

}
