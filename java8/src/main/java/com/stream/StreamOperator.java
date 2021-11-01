package com.stream;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * map reduce filter
 */
public class StreamOperator {

    @Test
    public void testMap() {
        Stream.of("apple", "banana", "orange")
                .map(val -> val.substring(0, 1).toUpperCase(Locale.ROOT).concat(val.substring(1)))
                .forEach(System.out::println);

        // string -> localDate 转换
        List<String> list = List.of("2020-01-01", "2020-02-21");
        list.stream()
                .map(val -> LocalDate.parse(val, DateTimeFormatter.ISO_LOCAL_DATE))
                .forEach(date -> System.out.println(date.getClass().getName()));
    }

    @Test
    public void testFilter() {
        // filter 运算
        IntStream.rangeClosed(1, 10)
                .filter(val -> val % 2 == 0)
                .mapToDouble(val -> Math.pow(val ,2))
                .mapToInt(value -> (int)value)
                .forEach(System.out::println);
    }

    @Test
    public void testReduce() {
        // 聚合求值
        int val = IntStream.rangeClosed(1, 100)
                .reduce(Integer::sum)
                .getAsInt();
        // 1-100的和=5050
        System.out.println("val = " + val);
    }

    @Test
    public void sort() {
        int[] ints = generator();
        System.out.println(Arrays.toString(ints));
//        数值排序
//        Arrays.stream(ints)
//                .sorted()
//                .forEach(System.out::println);

//        字典排序
        List<String> collect = Stream.<String>generate(() -> UUID.randomUUID().toString().substring(0, 10))
                .limit(10)
                .sorted(String::compareToIgnoreCase).toList();

        System.out.println(collect);

    }

    @Test
    public void testOthers() {
//        其它操作符, 转换操作, 聚合操作
//         distinct skip concat flatMap count max min sum average match
        List<String> list = List.of("A", "B", "C", "tom", "jerry", "A");

        System.out.println(list.stream().distinct().toList());
        System.out.println(list.stream().skip(4).toList());
        System.out.println(list.stream().count());
        System.out.println(list.stream().max(String::compareToIgnoreCase).get());
        System.out.println(list.stream().min(String::compareToIgnoreCase).get());
        System.out.println(list.stream().allMatch(s -> s.endsWith("m")));
        System.out.println(list.stream().findFirst().get());
    }



    private int[] generator() {
        Random random = new Random();
        return IntStream.generate(() -> random.nextInt(100))
                .limit(100)
                .toArray();
    }


}
