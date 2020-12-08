package com.david.module.util.javas.JVMBench;


import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子可以通过JConsole 或者 jstat -gcutil [pid] 2000(2000ms每次) 1000 看产程序堆内存的eden，s0, s1，old区域等内存的释放情况
 * 或者使用 jstat -gccause [pid] 查看gc的原因
 */
public class JvmTest {

    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }


    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(30000);
            list.add(new OOMObject());
            //   System.gc();
            System.out.print(list.size() + "\n");
        }

    }


    public static void main(String[] args) throws Exception {
        fillHeap(10000);
    }

}
