package com.david.module.util.javas.concurrent;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {


    public static void testLock() {

        ReentrantLock reentrantLock = new ReentrantLock();

        /**
         * 每一次lock() ReentrantLock中的 Sync 的state就会加一，所以叫可重入锁
         */
        /**
         * 当unlock() 的数量与 lock()的数量不一致， 会抛出 IllegalMonitorStateException
         *      具体原因见 AbstractQueuedSynchronizer类
         *      protected final boolean tryRelease(int releases) 方法
         */
        reentrantLock.lock();
        reentrantLock.lock();
        reentrantLock.unlock();
        reentrantLock.unlock();
        reentrantLock.unlock();


        Semaphore semaphore = new Semaphore(10);

        CountDownLatch countDownLatch = new CountDownLatch(3);
    }


    /**
     * 演示两个线程争抢锁
     */
    public static void testLock2() {

        ReentrantLock reentrantLock = new ReentrantLock();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();

            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
            }
        });

        thread1.start();
        thread2.start();

    }


    public static void main(String[] args) {

        testLock2();
        testLock();


    }
}
