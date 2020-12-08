package com.david.module.util.javas.concurrent;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class javaQueue {

    /**
     * 无界blockedQueue
     * todo 既然提供了LinkedBlockingQueue 为什么还要提供ConcurrentLinkedQueue ???!!!
     */
    static void testUnboundQueue() {

        // capacity写了就是有界的，不写，就是无界限的（下面是容量为1的有界限queue）
        //LinkedBlockingQueue unboundBlockQueue = new LinkedBlockingQueue(1);

        // 容量为2<sup>31</sup>-1 的容器
        LinkedBlockingQueue unboundBlockQueue = new LinkedBlockingQueue();

        // offer()方法不会阻塞当前线程，如果满了，当即就返回false
//        boolean isOK_0 = unboundBlockQueue.offer("元素1");
//        boolean isOK_1 = unboundBlockQueue.offer("元素2");
//        boolean isOK_2 = unboundBlockQueue.offer("元素3");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        // 当unboundBlockQueue中元素为0时候，take() poll()被block住了！！
                        // 线程状态变为 wait
                        String value = (String) unboundBlockQueue.poll(5, TimeUnit.SECONDS);
                        System.out.print("\n得到数据=" + value + "\n");
                    }
                } catch (InterruptedException e) {
                    // 当有thread.interrupt()操作时候，线程会抛出InterruptedException
                    // 好一点的线程操作都应该处理 Interrupted
                    e.printStackTrace();
                }
            }
        });


        try {
            // put()方法会阻塞当前线程  因为是无界的，所以永远都可以put进去
            unboundBlockQueue.offer("元素1");
            unboundBlockQueue.put("元素2");
            unboundBlockQueue.put("元素3");
//
            String result = (String) unboundBlockQueue.take();

            thread.start();

            //  thread.interrupt(); // 线程会报InterruptedException

            System.out.print("\nqueue中数据：" + unboundBlockQueue.toArray().length);
            System.out.print("\n启动了一个新的线程");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 模拟生产者消费者模式
     * 有界blockedQueue
     */
    static void testBoundQueue() {
        ArrayBlockingQueue boundBlockQueue = new ArrayBlockingQueue(2);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000, 1);
                        String e = (String) boundBlockQueue.take();
                        System.out.print("take掉了一个元素");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            boundBlockQueue.put("元素1");
            boundBlockQueue.put("元素2");
            boundBlockQueue.put("元素3");  // 这次操作会引起当前线程block
            System.out.print("put了第三个元素");
        } catch (InterruptedException e) {
            // 当前线程被 Interrupt
            e.printStackTrace();
        }

        System.out.print("end here!\n");
        while (true) ;
    }

    public static void main(String[] args) {

        testBoundQueue();

        testUnboundQueue();


    }
}
