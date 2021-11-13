package com.sync;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Main {

    @Test
    public void testSync() {
//         final List<String> list = new ArrayList<>();

         List list = Collections.synchronizedList(new ArrayList<>());
//        List<String> list = new CopyOnWriteArrayList<>();

        final ExecutorService service = Executors.newFixedThreadPool(100);

        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            service.execute(() -> {
                list.add("abc");
            });
        }

        service.shutdown();

        while(true) {
            if (service.isTerminated()) {
                // 1000个任务执行完成
                System.out.println(list.size());
                break;
            }
        }

        final long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + "ms");
    }

}
