package com.thread.util;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestUtil {

    public static void main(String[] args) {
        testLongAdder();
        testLongAccumulator();
    }

    private static void testLongAccumulator() {
        // 原子操作类
        // LongAccumulator lal = new LongAccumulator()
    }


    public static void testLongAdder() {
        LongAdder adder = new LongAdder();

        new Thread(() -> {
            IntStream.rangeClosed(1,10000)
                    .forEach(val -> {
                        adder.increment();
                    });
        }).start();

        new Thread(() -> {
            IntStream.rangeClosed(1,10000)
                    .forEach(val -> {
                        adder.increment();
                    });
        }).start();
        ThreadUtil.safeSleep(1000);

        System.out.println(adder.intValue());
        System.out.println(adder.sum());
    }
}
