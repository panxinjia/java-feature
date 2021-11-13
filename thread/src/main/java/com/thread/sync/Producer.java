package com.thread.sync;

import cn.hutool.core.thread.ThreadUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Producer implements Runnable{
    private Product product;
    private int cnt;
    public Producer(Product product) {
        this.product = product;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            synchronized (product) {
                while(product.isFlag()) {
                    product.wait(); // wait 释放同步监视器(锁), sleep 不会释放
                }
                if (cnt % 2== 0) {
                    product.setBrand("费列罗");
                    product.setName("巧克力");
                }else {
                    product.setBrand("哈尔滨");
                    product.setName("啤酒");
                }
                System.out.println("生产者 [ " + Thread.currentThread().getName() + "] -> " +
                        "生产了 " + product.getBrand() + "--" + product.getName());
                // ThreadUtil.sleep(100);
                cnt++;
                product.setFlag(true);
                product.notifyAll(); // 唤醒等待池中的一个, 可能会造成死锁
            }
        }
    }
}
