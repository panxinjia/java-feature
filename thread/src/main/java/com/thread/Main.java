package com.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    private static final TimeInterval timer = DateUtil.timer();

    @SneakyThrows
    public static void main(String[] args) {
        // 继承Thread

        timer.start();

        for (int i = 0; i < 10; i++) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " -> " + i);
        }

        new TestThread().start();

        Thread.sleep(10);

        for (int i = 0; i < 10; i++) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " -> " + i);
        }
        System.out.println(timer.intervalRestart() + " ms");
    }

    @Test
    public void testRunnable() {
        // 实现Runnable接口
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        }, "runnable线程 -> ").start();

        // 线程操作工具
        ThreadUtil.sleep(1000);

        Thread.currentThread().setName("main 线程 -> ");
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }

    }

    @Test
    public void saleTicket() {
        // 售票例子
        Runnable task = new Runnable() {
            private int tickets;

            {
                tickets = 10;
            }

            @Override
            public void run() {
                this.sale();
            }

            // 可见性操作  原子性操作
            public synchronized void sale() {
                while (true) {
                    // 同步代码块

                        if (tickets > 0) {
                            ThreadUtil.sleep(20);
                            System.out.println(Thread.currentThread().getName() + " 卖出第[" + tickets-- + "]张票");
                        } else {
                            break;
                        }
                    }
            }

        };

        new Thread(task, "01 -> ").start();
        new Thread(task, "02 -> ").start();
        new Thread(task, "03 -> ").start();

        ThreadUtil.safeSleep(3000);
    }

    @Test
    public void testCallable() {

        timer.start();
        FutureTask<Integer> task = new FutureTask<>(() -> {
            ThreadUtil.sleep(3000);
            return new Random().nextInt(100);
        });

        new Thread(task).start();

        try {
            final Integer val = task.get();
            System.out.println("val = " + val);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("done , time => " + timer.intervalRestart() + "ms");
    }

    @Test
    public void testPriority() {
        // 根据优先级调度线程
//        Stream<Thread> stream = Stream.<Thread>generate(() -> new Thread(() -> {
//                    for (int i = 0; i < 10; i++) {
//                        System.out.println(Thread.currentThread().getName() +
//                                ", priority -> " + Thread.currentThread().getPriority() + " => " + i);
//                    }
//                })).limit(10)
//                .peek(t -> t.setPriority((int) (Math.random() * 10 + 1)));
//
//        stream.collect(Collectors.toSet())
//                .forEach(Thread::start);
//
//        LockSupport.park();

        // 调度可能性最高
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " | " +
                        Thread.currentThread().getPriority() + " | " + i);
            }
        });

        t1.setPriority(Thread.MAX_PRIORITY);

        // 调度可能性最低
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                if (i == 10) {
                    try {
                        // t1 加入到t2前, t2完成之后, t1继续执行
                        t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " | " +
                        Thread.currentThread().getPriority() + " | " + i);
            }
        });

        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();

        // LockSupport.park();
    }

    @Test
    public void testDeadLock() {

        TaskRunnable task = new TaskRunnable();
        task.setFlag(true);

        TaskRunnable task2 = new TaskRunnable();
        task2.setFlag(false);

        new Thread(task).start();
        new Thread(task2).start();

        // LockSupport.park();

        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            // 同步区域
        }finally {
            lock.unlock();
        }

        if (lock.tryLock()) { // lock 被锁定时, 不会进入阻塞状态, 立即向下执行
            try {
                // 同步代码块
            }finally {
                lock.unlock();
            }
        }

        // 读写互斥, 写写互斥, 读写分离, 读读并发
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        // 读锁
        final Lock read = readWriteLock.readLock();
        // 写锁
        final Lock write = readWriteLock.writeLock();


    }

    static class TaskRunnable implements Runnable {
        // 类成员, 多个实例可以共享锁, 互相占用对方资源, 会造成死锁状态
        private static final Object Lock_A = new Object();
        private static final Object Lock_B = new Object();
        boolean flag = true;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
        @Override
        public void run() {
            while (true) {
                if (flag) {
                    synchronized (Lock_A) {
                        synchronized (Lock_B) {
                            System.out.println(Thread.currentThread().getName() + " -> " + flag);
                        }
                    }
                }else {
                    synchronized (Lock_B) {
                        synchronized (Lock_A) {
                            System.out.println(Thread.currentThread().getName() + " -> " + flag);
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testDaemon() {

        final Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " output");
        });
        t.setDaemon(false);
        t.start();


        System.out.println("main end");


    }
}
