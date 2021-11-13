package com.thread.sync;

import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Consumer implements Runnable {

    private Product product;

    public Consumer(Product product) {
        this.product = product;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            synchronized (product) {
                while (!product.isFlag()) { // 没有商品, 消费者等待
                    product.wait();
                }
                System.out.println("消费者 [" + Thread.currentThread().getName() +
                        "] 消费了 -> " + product.getBrand() + " ------ " + product.getName());
                // ThreadUtil.sleep(100);
                product.setFlag(false); // 消费完成, 没有商品
                product.notifyAll(); // 唤醒等待池中的一个, 可能会造成死锁
            }
        }
    }
}
