package com.thread;

import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestThread extends Thread{

    {
        this.setName("testThread -> ");
    }
    @Override
    public void run() {
        IntStream.rangeClosed(1, 10)
                .forEach(val -> {
                    System.out.println(Thread.currentThread().getName() + "" + val);
                });
    }
}
