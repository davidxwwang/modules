package com.david.module.util.javas.JVMBench;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * 栈测试
 */
public class JStackTest {


    /**
     * 线程死循环，可以模拟长时间的io操作
     */
    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //    while (true);  // 死循环
            }
        }, "testBusyThread");
        thread.start();
    }

    /**
     * 线程锁等待
     *
     * @param lock
     */
    public static void createLockedThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //   等待另外的线程对*lock*执行notify()或者notifyAll()
                //   如果得不到其他线程的通知，那么该线程将一直等下去
                try {
                    System.out.print("当前线程：" + Thread.currentThread().getName());
                    // 在这里必须使用 synchronized 等方式，获取对象的moniter（锁），才能正确的调用wait()/ notify()等方法，
                    // 不然会抛出 IllegalMonitorStateException
                    //  the current thread is not the owner of the object's monitor.
                    synchronized (lock) {
                        lock.wait();
                    }
                    //  lock.wait();
                    System.out.print("我想动一动，但是动不了！！！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread");
        //thread.run(); run()方法还是在原线程执行，而start才真正的开启了一个新线程
        thread.start();
    }

    // 相当于一个任务
    static class RunAddRunable implements Runnable {
        int a, b;

        public RunAddRunable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.print("结果 = " + (a + b));
                }
            }
        }
    }

    // Thread-4 被* @70961424 blocked（thread5持有）*

    /**
     * 名称: Thread-4
     * 状态: java.lang.Integer@70961424上的BLOCKED, 拥有者: Thread-5
     * 总阻止数: 2, 总等待数: 0
     * <p>
     * 堆栈跟踪:
     * JVMBench.JStackTest$RunAddRunable.run(JStackTest.java:72)
     * - 已锁定 java.lang.Integer@2932f26c
     * java.lang.Thread.run(Thread.java:748)
     * <p>
     * <p>
     * <p>
     * 名称: Thread-5
     * 状态: java.lang.Integer@2932f26c上的BLOCKED, 拥有者: Thread-4
     * 总阻止数: 1, 总等待数: 0
     * <p>
     * 堆栈跟踪:
     * JVMBench.JStackTest$RunAddRunable.run(JStackTest.java:72)
     * - 已锁定 java.lang.Integer@70961424
     * java.lang.Thread.run(Thread.java:748)
     */
    public static void testDeadLock() {
        for (int i = 0; i < 3000; i++) {
            new Thread(new RunAddRunable(1, 2)).start();
            new Thread(new RunAddRunable(2, 1)).start();
        }
    }


    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    /**
     * 无限循环的方法
     *
     * @throws InterruptedException
     */
    public static void testStackUnlimitLoop() throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        list.add(new OOMObject());
        Thread.sleep(20);
        testStackUnlimitLoop();

    }


    public static void main(String[] args) throws Exception {


        createBusyThread();

        testStackUnlimitLoop();

        Thread.sleep(30000);
        testDeadLock();

        Object objectLock = new Object();
        createLockedThread(objectLock);

        Thread.sleep(60000);

        // 在这里必须使用 synchronized 等方式，获取对象的moniter（锁），才能正确的调用wait()/ notify()等方法，
        // 不然会抛出 IllegalMonitorStateException
        //  the current thread is not the owner of the object's monitor.
        // A "monitor" is a mechanism that ensures that only one thread can be executing a given section (or sections) of code at any given time.
        // This can be implemented using a lock (and "condition variables" that allow threads to wait for or send notifications to other threads that the condition is fulfilled),
        // but it is more than just a lock. Indeed, in the Java case, the actual lock used by a monitor is not directly accessible.
        // (You just can't say "Object.lock()" to prevent other threads from acquiring it ... like you can with a Java Lock instance.)
        // https://stackoverflow.com/questions/9848616/whats-the-meaning-of-an-objects-monitor-in-java-why-use-this-word
        synchronized (objectLock) {
            objectLock.notify();
        }

        // objectLock.notify();

        /**
         * 仅仅是为了main线程不退出
         */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // 等待键盘输入
        bufferedReader.readLine();
        // bufferedReader.readLine();

    }
}
