package com.david.module.util.javas.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * AbstractQueuedSynchronizer的使用demo
 * 它的特点就使用一个int类型的数据，就可以完成同步的工作，那么像：信号量/ 多线程下的计数器/ 倒计时 都可以做了
 * // 多线程环境下的状态机也是可以的
 * <p>
 * <p>
 * <p>
 * <p>
 * 原理： CLH锁也是一种基于链表的可扩展、高性能、公平的自旋锁，
 */
public class SubClassAbstractQueuedSynchronizer {

//  (1)  synchronizers that rely on a single atomic {@code int} value to represent state.
//  (2)  rely on first-in-first-out (FIFO) wait queues
//  (3)  Subclasses
// * must define the protected methods that change this state, and which
// * define what that state means in terms of this object being acquired
// * or released.

    // 类似一把互斥锁（mutex），0 表示没有锁定； 1 表示锁定
    private static class Sync extends AbstractQueuedSynchronizer {
        // Reports whether in locked state
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 试图去获取锁，将state置为1
        // 多线程下，为什么它可以正常工作： 因为compareAndSetState 是一个原子性的操作。
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 试图去释放锁，将state置为0
        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0); // 设置锁的状态为0
            return true;
        }

        // Provides a Condition
        Condition newCondition() {
            return new ConditionObject();
        }

        // Deserializes properly
        private void readObject(ObjectInputStream s)
                throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    public static void main(String[] args) {

        Sync lockSync = new Sync();

        // is_1=true is_11= false 因为状态值已经被设置为1了，所以is_11 不可能设置对
        boolean is_1 = lockSync.tryAcquire(0);
        boolean is_11 = lockSync.tryAcquire(0);

        boolean is_0 = lockSync.tryRelease(1);

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(() -> {


            });

            thread.start();
        }

    }

}
