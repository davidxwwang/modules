
//A thread state. A thread can be in one of the following states:
//        NEW
//        A thread that has not yet started is in this state.
//        RUNNABLE
//        A thread executing in the Java virtual machine is in this state.
//        BLOCKED
//        A thread that is blocked waiting for a monitor lock is in this state.
//        WAITING
//        A thread that is waiting indefinitely for another thread to perform a particular action is in this state.
//        TIMED_WAITING
//        A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state.
//        TERMINATED
//        A thread that has exited is in this state.
//        A thread can be in only one state at a given point in time. These states are virtual machine states which do not reflect any operating system thread states.

//  https://fangjian0423.github.io/2016/06/04/java-thread-state/

package com.david.module.util.javas.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class TestThread {

    static Logger logger = LoggerFactory.getLogger("TestThread");


    static public void testTwoThreadStart() {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                // 任务一结束，线程马上就 TERMINATED
                System.out.print(Thread.currentThread() + " is running");
            }
        }, "RUNNABLE -Thread");
        /**
         *  连续两次线程start()会报 "IllegalThreadStateException"
         */
        thread1.start();
        thread1.start();
    }


    static public void testThread() {

        ArrayBlockingQueue queue = new ArrayBlockingQueue<Runnable>(2);

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread();
            try {
                queue.take();
                queue.put(thread);
            } catch (InterruptedException ex) {
                logger.info("david " + ex.toString());
            }
        }


        Thread thread = new Thread();
        System.out.print("state= " + thread.getState());


//        try{
//            Thread.currentThread().sleep(100);
//        }catch (InterruptedException ex){
//
//        }
//
//        try{
//            thread1.wait();
//        }catch (InterruptedException ex){
//
//        }

        final Object lock = new Object();
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    logger.info("{} invoke", Thread.currentThread());
                    try {
//                        wait();
                        Thread.sleep(2000L);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }, "BLOCKED -Thread-3");

        Thread thread4 = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    logger.info("{} invoke", Thread.currentThread());
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        }, "BLOCKED -Thread-4");
        thread3.start();
        thread4.start();

        System.out.print("state= " + thread3.getState());
        System.out.print("state= " + thread4.getState());
        logger.info("thread3 invoke state= {}", thread3.getState());
        // thread4 in "blocked" A thread that is blocked waiting for a monitor lock is in this state.
        logger.info("thread4 invoke state= {}", thread4.getState());

        System.out.print("state= " + thread.getState());

    }


    public static void main(String[] args) {

        testTwoThreadStart();

        testThread();

    }
}
