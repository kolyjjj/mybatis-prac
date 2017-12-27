package li.koly;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

// reference: http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
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
        Integer sum = list.stream().collect(new Collector<Integer, Integer[], Integer>() {


            @Override
            public Supplier<Integer[]> supplier() {
                return () -> new Integer[1];
            }

            @Override
            public BiConsumer<Integer[], Integer> accumulator() {
                return (integers, integer) -> integers[0] = integers[0] + 1;
            }

            @Override
            public BinaryOperator<Integer[]> combiner() {
                return new BinaryOperator<Integer[]>() {
                    @Override
                    public Integer[] apply(Integer[] integers, Integer[] integers2) {
                        integers[0] += integers2[0];
                        return integers;
                    }
                };
            }

            @Override
            public Function<Integer[], Integer> finisher() {
                return integers -> integers[0];
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
            }
        });
        assertThat(sum).isEqualTo(10);
    }

    @Test
    public void should_map() {
        List<Integer> list = prepareList();
        List<String> result = list.stream().map(i -> i.toString()).collect(Collectors.toList());
        assertThat(result).containsExactly("1", "2", "4", "3");
    }


    @Test
    public void should_distinct() {
        List<Integer> list = prepareList();
        list.add(2);
        List<Integer> distinct = list.stream().distinct().collect(Collectors.toList());
        assertThat(distinct).containsExactly(1, 2, 4, 3);
    }

    @Test
    public void should_sorted_return_sorted_stream() {
        List<Integer> list = prepareList();
        List<Integer> result = list.stream().sorted().collect(Collectors.toList());
        assertThat(result).containsExactly(1, 2, 3, 4);
    }

    @Test
    public void should_peek_usually_for_logging() {
        List<Integer> list = prepareList();
        List<Integer> peek = list.stream().peek(i -> System.out.println(i)).peek(i -> i.toString()).collect(Collectors.toList());
        assertThat(peek).containsExactly(1, 2, 4, 3);
    }

    @Test
    public void should_limit_stream() {
        List<Integer> list = prepareList();
        List<Integer> limit = list.stream().limit(2).collect(Collectors.toList());
        assertThat(limit).containsExactly(1, 2);
    }


    @Test
    public void should_skip_the_first_n_elements_and_return_left() {
        List<Integer> list = prepareList();
        List<Integer> skip = list.stream().skip(2).peek(System.out::println).collect(Collectors.toList());
        assertThat(skip).containsExactly(4, 3);
    }


    @Test
    public void should_foreach_which_returns_void() {
        List<Integer> list = prepareList();
        list.stream().forEach(i -> System.out.println(i));
    }


    @Test
    public void should_foreach_ordered_which_returns_void() {
        List<Integer> list = prepareList();
        list.stream().forEachOrdered(i -> System.out.println(i));
    }


    @Test
    public void should_max() {
        List<Integer> list = prepareList();
        Optional<Integer> max = list.stream().max(Comparator.naturalOrder());
        assertThat(max.get()).isEqualTo(4);
    }


    @Test
    public void should_count() {
        List<Integer> list = prepareList();
        long count = list.stream().count();
        assertThat(count).isEqualTo(4);
    }


    @Test
    public void should_any_match() {
        List<Integer> list = prepareList();
        boolean matched = list.stream().anyMatch(x -> x == 4);
        assertThat(matched).isTrue();
    }


    @Test
    public void should_all_match() {
        List<Integer> list = prepareList();
        boolean allMatched = list.stream().allMatch(x -> x > 0);
        assertThat(allMatched).isTrue();
    }


    @Test
    public void should_non_match() {
        List<Integer> list = prepareList();
        boolean nonMatched = list.stream().noneMatch(x -> x < 0);
        assertThat(nonMatched).isTrue();
    }


    @Test
    public void should_find_first() {
        List<Integer> list = prepareList();
        Optional<Integer> first = list.stream().findFirst();
        assertThat(first.get()).isEqualTo(1);

        Optional<Integer> first1 = list.stream().filter(x -> x > 3).findFirst();
        assertThat(first1.get()).isEqualTo(4);
    }


    @Test
    public void should_find_any() {
        List<Integer> list = prepareList();
        Optional<Integer> any = list.stream().findAny();
        assertThat(any.get()).isEqualTo(1);
    }


    @Test
    public void should_create_empty_stream() {
        Stream<Object> empty = Stream.empty();
    }


    @Test
    public void should_of() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        List<Integer> list = stream.collect(Collectors.toList());
        assertThat(list).containsExactly(1, 2, 3, 4);
    }


    @Test
    public void should_iterate() {
        List<Integer> iterate = Stream.iterate(1, integer -> integer + 1).limit(5).collect(Collectors.toList());
        assertThat(iterate).containsExactly(1, 2, 3, 4, 5);
    }


    @Test
    public void should_generate_infinitely() {
        Stream<Integer> infinite = Stream.generate(() -> 1);
        List<Integer> infi = infinite.limit(5).collect(Collectors.toList());
        assertThat(infi).containsExactly(1, 1, 1, 1, 1);
    }


    @Test
    public void should_concat() {
        Stream<Integer> one = Stream.of(1, 2);
        Stream<Integer> two = Stream.of(3, 4);
        Stream<Integer> result = Stream.concat(one, two);
        assertThat(result).containsExactly(1, 2, 3, 4);
    }


    @Test
    public void should_parallel() {
        List<Integer> list = prepareList();
        List<String> paralMap = list.stream().parallel().map(Object::toString).collect(Collectors.toList());
        assertThat(paralMap).containsExactly("1", "2", "4", "3");
    }


    @Test
    public void should_parallel_whoo() {
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
    }

    @Test
    public void should_lazy() {
        Stream.of(1, 2, 3).filter(x -> {
            System.out.println("filter " + x);
            return x > 1;
        }).map(x -> {
            System.out.println("map " + x);
            return "a" + String.valueOf(x);
        });
    }

    @Test
    public void should_lazy_until_terminal() {
        Stream.of(1, 2, 3).filter(x -> {
            System.out.println("filter " + x);
            return x > 1;
        }).map(x -> {
            System.out.println("map " + x);
            return "a" + String.valueOf(x);
        }).forEach(System.out::println);
    }

    @Test
    public void should_use_stream_build() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    }

    @Test
    public void should_flat_map() {
        List<Enterprise> enterpriseList = new ArrayList<>();
        IntStream.range(1, 4)
                .forEach(i -> enterpriseList.add(new Enterprise("enter " + i)));
        enterpriseList
                .forEach(e -> IntStream.range(1, 4)
                        .forEach(i -> e.users.add(new User("user " + i))));

        enterpriseList.stream().flatMap(e -> e.users.stream()).forEach(u -> System.out.println(u.name));
    }

    @Test
    public void should_flat_map_fluently() {
        IntStream.range(1, 4)
                .mapToObj(i -> new Enterprise("enter " + i))
                .peek(e -> IntStream.range(1, 4).mapToObj(i -> new User("user " + i)).forEach(e.users::add))
                .flatMap(e -> e.users.stream())
                .forEach(u -> System.out.println(u.name));
    }

    @Test
    public void should_optional_flat_map() {
        Outer outer = new Outer();
        if (outer != null && outer.nested != null && outer.nested.inner != null) {
            System.out.println(outer.nested.inner.name);
        }

        // instead
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.name))
                .ifPresent(System.out::println);
    }

    class Outer {
        Nested nested;
    }

    class Nested {
        Inner inner;
    }

    class Inner {
        String name;
    }


    private static class Enterprise {
        String name;
        List<User> users = new ArrayList<>();

        public Enterprise(String name) {
            this.name = name;
        }
    }

    private static class User {
        String name;

        User(String name) {
            this.name = name;
        }
    }


    private List<Person> persons = Arrays.asList(
            new Person("Max", 18),
            new Person("Peter", 23),
            new Person("Pamela", 23),
            new Person("David", 12)
    );

    @Test
    public void should_collect_grouping_by() {
        Map<Integer, List<Person>> personsByAge = persons.stream().collect(Collectors.groupingBy(p -> p.age));
        personsByAge.forEach((age, p) -> System.out.format("age %d : %s\n", age, p));
    }

    @Test
    public void should_collect_average_int() {
        Double averageAge = persons.stream().collect(Collectors.averagingInt(p -> p.age));
        System.out.println(averageAge);
    }

    @Test
    public void should_collect_summarize() {
        IntSummaryStatistics statistics = persons.stream().collect(Collectors.summarizingInt(p -> p.age));
        System.out.println(statistics);
    }

    @Test
    public void should_collect_joining() {
        String result = persons.stream()
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In China", " are of legal age"));
        System.out.println(result);
    }

    @Test
    public void should_collect_to_map() {
        Map<Integer, String> map = persons.stream()
                .collect(Collectors.toMap(p -> p.age, p -> p.name, (name1, name2) -> name1 + " ; " + name2));
        System.out.println(map);
    }

    @Test
    public void should_use_collector_of() {
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher
        String collect = persons.stream()
                .collect(personNameCollector);

        System.out.println(collect);
    }


    class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
