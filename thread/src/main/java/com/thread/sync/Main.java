package com.thread.sync;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {
    public static void main(String[] args) {

        Product product = new Product();

        Producer producer = new Producer(product);
        Consumer consumer = new Consumer(product);

        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();

        LockSupport.park();
    }
}
