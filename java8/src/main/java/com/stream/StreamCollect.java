package com.stream;

import org.junit.jupiter.api.Test;

import javax.naming.NamingSecurityException;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollect {

    private static final List<String> NAMES = List.of("tom", "Jerry", "Tom", "bob", "stoll", "zero");

    @Test
    public void collectToList() {
        // 聚合求值, 收集为集合
        List<String> names = NAMES.stream().map(name -> name.toUpperCase(Locale.ROOT)).toList();
        names.forEach(System.out::println);
    }

    @Test
    public void collectToArray() {
        // 聚合求值, 收集为数组
        String[] names = NAMES.toArray(String[]::new);
        System.out.println(Arrays.toString(names));
    }

    @Test
    public void collectToMap() {
        Map<String, List<Character>> map = NAMES.stream()
                .collect(Collectors.toMap(
                        // 流中元素到key的映射
                        key -> key.substring(0, 1).toUpperCase(Locale.ROOT).concat(UUID.randomUUID().toString().substring(0, 10)),
                        // 流中元素到value的映射
                        value -> value.chars().mapToObj(value1 -> (char) value1).toList()
                ));

        // 转换
        System.out.println(map);
    }

    @Test
    public void groupBy() {
        // 分组运算
        Map<String, List<String>> groupingBy = NAMES.stream()
                .collect(Collectors.groupingBy(s -> s.substring(0, 1).toUpperCase(Locale.ROOT), Collectors.toList()));
        System.out.println(groupingBy);
    }
}
