package com.thread;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestThreadLocal {

    @Test
    public void testThreadUtil() {
        ExecutorService executor = ThreadUtil.newExecutor();
        System.out.println(executor.getClass().getName());
        Runnable run = ThreadUtil.execAsync(() -> {
            System.out.println("execAsync");
        }, false);


    }

    @SneakyThrows
    public static void main(String[] args) {
//        IntStream.rangeClosed(1, 100)
//                .forEach(val -> {
//                    new Thread(() -> {
//                        UserContext.setUserName(Thread.currentThread().getName() + " -> " + val);
//                        while (true) {
//                            final String name = UserContext.currentUserName();
//                            System.out.println(name);
//                            ThreadUtil.sleep(2000);
//                        }
//
//                    }).start();
//                });
//
//        Thread.sleep(10_0000);

        try (var ctx = new UserContext()) {
            ctx.setUserName("tom");

            // todo 使用线程上下文变量

            // 离开try块 自动调用 ctx.close 函数
        }finally {
            // todo
            System.out.println("finally");
        }

    }
}

class UserContext implements AutoCloseable {

    // 线程上下文变量
    public static final ThreadLocal<String> tl = new ThreadLocal<>();


    public String currentUserName() {
        return tl.get();
    }

    public void setUserName(String name) {
        tl.set(name);
    }

    @Override
    public void close() throws Exception {
        // trw 退出try块时会自动被调用
        System.out.println("close 自动调用");
        tl.remove();
    }
}
