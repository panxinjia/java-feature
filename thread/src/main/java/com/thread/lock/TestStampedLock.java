package com.thread.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestStampedLock {


    static class Point {
        private static final StampedLock STAMPED_LOCK = new StampedLock();
        private double x;
        private double y;

        public void move(double x, double y) {
            long stamp = STAMPED_LOCK.writeLock();
            try {
                this.x += x;
                this.y += y;
            }finally {
                STAMPED_LOCK.unlockWrite(stamp);
            }
        }

        public double distanceFromOrigin() {
            long read = STAMPED_LOCK.tryOptimisticRead();
            double currentX = this.x;
            double currentY = this.y;

            // 值被修改过, 加悲观锁,重新读取值并计算
            if (!STAMPED_LOCK.validate(read)) {
                read = STAMPED_LOCK.readLock();
                try {
                    currentX = this.x;
                    currentY = this.y;
                }finally {
                    // 释放读取锁 
                    STAMPED_LOCK.unlockRead(read);
                }
            }

            return Math.sqrt(Math.pow(currentX, 2) + Math.pow(currentY, 2));
        }
    }

}

