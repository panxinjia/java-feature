package com.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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

    }
}
