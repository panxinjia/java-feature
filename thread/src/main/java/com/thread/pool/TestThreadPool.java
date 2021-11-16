package com.thread.pool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestThreadPool {

    @Test
    public void testThreadPool() {
//
//        ThreadPoolExecutor executor= (ThreadPoolExecutor) Executors.newFixedThreadPool(100);

//        final List<String> list = new ArrayList<>();
//        while(true) {
//            executor.execute(() -> {
//                list.add(UUID.randomUUID().toString());
//            });
//            ThreadUtil.sleep(100);
//        }

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                20,
                60,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new DiscardPolicyImpl());
    }

    static class DiscardPolicyImpl extends ThreadPoolExecutor.DiscardPolicy {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            // super.rejectedExecution(r, e);
            final BlockingQueue<Runnable> queue = e.getQueue();
            while (!queue.offer(r)) {
                // 将任务再次尝试重新放入线程池
            }
        }
    }

    @Test
    @SneakyThrows
    public void testSchedule() {
        // 延迟任务
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10, Executors.defaultThreadFactory());

        executor.schedule(new DealyTask("task1",1), 1L, TimeUnit.SECONDS);
        executor.schedule(new DealyTask("task2",2), 2L, TimeUnit.SECONDS);
        executor.schedule(new DealyTask("task3",3), 3L, TimeUnit.SECONDS);
        executor.schedule(new DealyTask("task4",10), 10L, TimeUnit.SECONDS);

        executor.shutdown();

        while (executor.isTerminated()) {
            System.out.println("done");
        }
    }

    static class DealyTask implements Runnable, Delayed {

        private String name;
        private long delay;

        public DealyTask(String name, long delay) {
            this.name = name;
            this.delay = delay;
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.SECONDS) - o.getDelay(TimeUnit.SECONDS));
        }

        @Override
        public void run() {
            System.out.println("延迟 [ " + this.delay +" ] 执行");
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return this.delay;
        }
    }

    @Test
    @SneakyThrows
    public void testForkJoinTask() {

        final Task task = new Task(1, 10000);
        final ForkJoinPool pool = new ForkJoinPool();
        final TimeInterval timer = DateUtil.timer();
        timer.start();
        pool.submit(task);
        // 阻塞读取结果值
        final Integer result = task.get();
        System.out.println("result = " + result);
        final long time = timer.intervalRestart();
        System.out.println(time);

        pool.shutdown();
    }

    static class Task extends RecursiveTask<Integer> {
        private Integer start;
        private Integer end;
        private Integer result;

        public Task(Integer start, Integer end, Integer... result) {
            this.start = start;
            this.end = end;
            this.result = Arrays.stream(result).reduce(Integer::sum).orElse(0);
        }

        @Override
        protected Integer compute() {
            if (end - start <= 100) {
                for (int i = start; i <= end; i++) {
                    result += i;
                }
            } else {
                int middle = start + 100;
                final Task task1 = new Task(start, middle);
                final Task task2 = new Task(middle + 1, end);
                task1.fork();
                task2.fork();
                result = task1.join() + task2.join();
            }

            return result;
        }
    }

    @Test
    public void testDaemon() {
        System.out.println("start");

        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        final Thread t = new Thread(() -> {
            while (true) {
                String now = format.format(new Date());
                System.out.println(now);
                ThreadUtil.sleep(1000);
            }
        });

        // 守护线程在没有前台线程时自动结束
        t.setDaemon(true);
        t.start();

        ThreadUtil.sleep(5000);
        System.out.println("end");


    }
}
