package com.stream;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamBuild {

    /**
     * 有限序列
     */
    @Test
    public void build1() {
        // 枚举值构造
        Stream<String> stream = Stream.of("A", "B", "C", "D");
        stream.forEach(System.out::println);

        // builder模式
        Stream<String> stream2 = Stream.<String>builder()
                .add("Byte")
                .add("Short")
                .add("Integer")
                .add("Character")
                .add("Float")
                .add("Double")
                .add("Boolean")
                .build();

        stream2.forEach(System.out::println);
    }

    /**
     * 基于数组或容器构造有限序列
     */
    @Test
    public void build2() {
        IntStream stream = Arrays.stream(new int[]{1, 2, 3, 4, 5});
        Stream<String> stream2 = List.of("tom", "jerry", "bob").stream();
        stream.forEach(System.out::println);
        stream2.forEach(System.out::println);
    }

    /**
     * 构造无限序列, 无限序列求值前需要先处理成有限序列, 通过 limit skip find* match 操作符操作序列
     * <p>
     * 无限序列中间过程不会发生计算, 发生求值操作才会真正计算最终结果
     */
    @Test
    public void build3() {
        // 基于一个计算函数构造无限流
        Stream<Double> generate = Stream.<Double>generate(Math::random);
        long count = generate.limit(10)
                .count();

        System.out.println("count = " + count);

        // 基于一个 seed(种子构造无限流)
        Stream<Double> iterate = Stream.<Double>iterate(2.0, val -> Math.pow(val, 2));
        iterate.limit(10)
                .forEach(System.out::println);

        // api调用返回Stream
        String path = "/Users/xiaopantx/IdeaProjects/java-feature/java8/src/main/java/com/stream/StreamBuild.java";
        try (Stream<String> stringStream = Files.lines(Paths.get(path))) {
            stringStream.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 基础流 (为了避免频繁的装箱和拆箱操作, java对基本类型提供了标准的流生成方法)
        // 基础流操作方式和Stream操作方式相同
        IntStream intStream = IntStream.range(1, 10);
        DoubleStream doubleStream = DoubleStream.generate(Math::random);
        LongStream longStream = LongStream.range(1, Integer.MAX_VALUE);

        // 标准流转换
        DoubleStream doubleStream1 = intStream.asDoubleStream();
        LongStream longStream1 = intStream.asLongStream();
    }

    /**
     * 可以产生一个斐波那契数列的LongStream
     */
    @Test
    public void build4() {
        LongStream longStream = LongStream.<Long>generate(new LongSupplier() {
            long a1 = 0;
            long a2 = 0;
            long a3 = 1;
            @Override
            public long getAsLong() {
                a1 = a2;
                a2 = a3;
                a3 = a1 + a2;
                return a1;
            }
        });

        longStream
                .limit(10)
                .forEach(System.out::println);
    }
}
