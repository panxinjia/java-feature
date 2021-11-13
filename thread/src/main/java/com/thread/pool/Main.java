package com.thread.pool;

import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    public static void main(String[] args) {
        FutureTask<Integer> future = new FutureTask<>(() -> {
            ThreadUtil.sleep(2000);
            return new Random().nextInt(100);
        });

        new Thread(future).start();

        while (true) {
            System.out.println(future.isDone());
            // 轮询, 判断等待, future的缺点
            while (future.isDone()) {
                try {
                    System.out.println(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (future.isDone())
                    break;
            }

            if (future.isDone())
                break;


        }

    }

    @Test
    @SneakyThrows
    public void testCompletableFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            System.out.println("子线程开始执行任务");
            ThreadUtil.sleep(5000);
            future.complete("异步任务执行完成");
            System.out.println("子线程done");
        }).start();

        System.out.println("主线程调用get方法, 阻塞获取值: " + future.get());
        System.out.println("done");
    }

    @Test
    @SneakyThrows
    public void runAsync() {
        // 无返回值异步任务
        Void val = CompletableFuture.<Void>runAsync(() -> {
            System.out.println("begin");
            ThreadUtil.sleep(2000);
            System.out.println("end");
        }).get();
        System.out.println("done");
    }

    @Test
    @SneakyThrows
    public void supplyAsync() {
        // 有返回值异步任务
        String value = CompletableFuture.<String>supplyAsync(() -> {
            System.out.println("begin");
            ThreadUtil.sleep(2000);
            return UUID.randomUUID().toString();
        }).get();

        System.out.println("value: " + value);
        System.out.println("done");
    }

    @Test
    @SneakyThrows
    public void thenApply() {
        // 串联异步任务
        final Integer i = 2;
        final CompletableFuture<Double> future = CompletableFuture.<Integer>supplyAsync(() -> i)
                .thenApply(val -> Math.pow(val, 2))
                .thenApply(val -> Math.pow(val, 2))
                .thenApply(val -> Math.pow(val, 2));

        // 256.0
        final Double val = future.get();
        System.out.println(val + " - " + (16 * 16.0));
    }

    @Test
    @SneakyThrows
    public void thenAccept() {
        // thenAccept 处理异步执行的结果  thenRun
        CompletableFuture
                .<Double>supplyAsync(() -> new Random().nextDouble(20))
                .thenApply(val -> val + new Random().nextDouble(20))
                .thenApply(val -> val + new Random().nextDouble(20))
                .thenAccept(System.out::println);

        System.out.println("<-------------------------------->");
        final CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture
                .<Double>supplyAsync(() -> {
                    ThreadUtil.sleep(5000);
                    return new Random().nextDouble(20);
                })
                .thenRun(() -> {
                    ThreadUtil.sleep(1000);
                    System.out.println("异步执行完成");
                    latch.countDown();
                });

        System.out.println("main done");
        latch.await();
    }

    @Test
    @SneakyThrows
    public void exceptionally() {
        //exceptionally  兜底异常
        CompletableFuture.<Integer>supplyAsync(() -> {
            System.out.println("1");
            return 1;
        }).thenApply(val -> {
            System.out.println("2");
            return val + 1;
        }).thenApply(val -> {
            System.out.println("3");
            return val + 1;
        }).thenAccept(result -> {
            System.out.println("输出结果: result = " + result);
        }).exceptionally(e -> {
            final String msg = e.getMessage();
            System.out.println("发生错误: msg = " + msg);
            return Void.TYPE.cast(null);
        });

        System.out.println("done");
    }

    @Test
    @SneakyThrows
    public void testThenComposeAndThenCombine() {
        //TODO 结果合并, 依赖关系, 无依赖关系结果合并

        //thenCompose 合并有依赖关系的两个结果
        CompletableFuture
                .supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.<Integer>supplyAsync(() -> i + 1))
                .thenAccept(result -> {
                    System.out.println("result = " + result);
                });

        // thenCombine 合并两个没有依赖关系的结果
        CompletableFuture
                .supplyAsync(() -> 1)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> 2), List::of)
                .thenAccept(result -> {
                    System.out.println("result = " + result);
                });

        // allOf anyOf
        CompletableFuture.allOf(
                        CompletableFuture.<Integer>supplyAsync(() -> {
                            System.out.println(1);
                            return 1;
                        }),
                        CompletableFuture.<Integer>supplyAsync(() -> {
                            System.out.println(2);
                            return 2;
                        }),
                        CompletableFuture.<Integer>supplyAsync(() -> {
                            System.out.println(3);
                            return 3;
                        }))
                .thenAccept(result -> {
                    System.out.println("allOf = " + result);
                })
                .exceptionally(Void.TYPE::cast);

        CompletableFuture.anyOf(
                        CompletableFuture.<Integer>supplyAsync(() -> {
                            System.out.println(1);
                            return 1;
                        }),
                        CompletableFuture.<Integer>supplyAsync(() -> {
                            System.out.println(2);
                            return 2;
                        }),
                        CompletableFuture.<Integer>supplyAsync(() -> {
                            System.out.println(3);
                            return 3;
                        }))
                .thenAccept(result -> {
                    System.out.println("anyOf = " + result);
                })
                .exceptionally(Void.TYPE::cast);
    }

}
