package com.javastudy.vendor.javas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS(V,E,N)。V 表示要更新的变量(内存值)，E 表示预期值(旧的)，N 表示新值
 * 当V==E时候，将V更新成N
 * 当V!=E时候，无法将V更新成N(说明有其他线程修改了它的值)
 */
public class CompareAndSetDemo {

    public static void testAtomicInteger() {

        AtomicInteger atomicInteger = new AtomicInteger(2);
        // 判断atomicInteger的数据是不是2，如果是2，将其更新为3（这里是OK的）
        boolean isOk = atomicInteger.compareAndSet(2, 3);
        // 得到数据是3
        Integer value = atomicInteger.get();

        // 这里无法将 atomicInteger修改成1000000，因为这个值已经被修改成3了，不再是2
        boolean isOk2 = atomicInteger.compareAndSet(2, 1000000);

        // 得到数据是3
        Integer value2 = atomicInteger.get();

        System.out.print("");
    }

}
