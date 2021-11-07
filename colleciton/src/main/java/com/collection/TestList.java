package com.collection;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.IntFunction;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestList {

    @Test
    public void testList() {
        List<String> list =new ArrayList<>();
        list.add("apple");
        list.add("orange");
        list.add("apple");
        // 允许null值
//        list.add(null);
        System.out.println(list);
        // 排列放入的元素
//        System.out.println(list.stream()
//                .sorted(Comparator.comparingInt(String::length)).toList());
        List<String> copy = List.copyOf(list);
        // copy.add("abc"); List.* 静态接口创建的list不可变

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String value = it.next();
            System.out.println(value);
        }

        System.out.println("-------->for each 基于迭代器遍历<----------");
        // java 编译器会将 for each语法转换成迭代器遍历
        for(String value: list) {
            System.out.println(value);
        }

        System.out.println("-------->容器转数组<----------");
        Object[] objects = list.toArray();
        String[] ss = list.toArray(String[]::new);
        System.out.println(Arrays.toString(ss));

        // == 内存地址是否相同, oop中表达是不是同一个实例, .equals 对象的相等逻辑

        List<Person> people = List.of(new Person("tom"), new Person("jerry"));
        boolean bool = people.contains(new Person("tom"));
        System.out.println(bool);

    }

    public static void main(String[] args) {
        // 构造从start到end的序列：
        final int start = 10;
        final int end = 20;
        List<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        Collections.shuffle(list);// 打乱容器元素顺序
        // 随机删除List中的一个元素:
        int removed = list.remove((int) (Math.random() * list.size()));
        int found = findMissingNumber(start, end, list);
        System.out.println(list.toString());
        System.out.println("missing number: " + found);
        System.out.println(removed == found ? "测试成功" : "测试失败");
    }

    static int findMissingNumber(int start, int end, List<Integer> list) {
        long startTime = System.nanoTime();
        int number = -1;
        for(int i = start; i <= end; i++) {
            // 是否包含元素
            if (!list.contains(i)) {
                number = i;
            }
        }

        System.out.println("运行时间: " + (System.nanoTime() - startTime) + " ns");
        return number;
    }

    //@EqualsAndHashCode
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class Person {
        private String name;
    }
}
