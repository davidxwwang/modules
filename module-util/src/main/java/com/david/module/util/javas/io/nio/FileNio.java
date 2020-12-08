package com.david.module.util.javas.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

// 在 NIO 库中，所有数据都是用缓冲区处理的。
public class FileNio {


    static final String file77Mb = "/Users/david/Documents/workSpace/jenkins2.war";
    static final String file91Kb = "/Users/david/Documents/workSpace/AMap_adcode_citycode.xlsx.zip";

    static final String writefilepath = "/Users/david/Documents/workSpace/david.txt";

    /**
     * 使用MappedByteBuffer作为缓存
     */
    public static void testRead0() {
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile(file77Mb, "rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();

            // 使用MappedByteBuffer比bytebuffer快多了！！！，但存在资源释放的问题
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());

            long timeEnd = System.currentTimeMillis();
            System.out.println("MappedByteBuffer  Read time: " + (timeEnd - timeBegin) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用ByteBuffer 作为缓存
     */
    public static void testRead() {

        FileInputStream fin = null;

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity() + "位置是：" + buffer.position());

        Long current = System.currentTimeMillis();

        try {

            fin = new FileInputStream(file77Mb);

//            while (fin.read()!= -1){
//            }

            FileChannel fc = fin.getChannel();

            int length = -1;
            while ((length = fc.read(buffer)) != -1) {
                //   System.out.print("读到的数据：" + buffer.toString());
//                System.out.println("限制是：" + buffer.limit() + "容量是：" + buffer.capacity()
//                        + "位置是：" + buffer.position());
                buffer.clear();

            }

            fc.close();

            // todo 读取一个77Mb的文件，NIO要比io快了差不多100倍！ 因为io是基于字节的，而nio是基于块的
            //      读取一个91k的文件，NIO/io的时间 = 317/41 = 7倍多
            System.out.print("读取文件共花去了时间是： " + String.valueOf(System.currentTimeMillis() - current) + "ms");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    public static void testWrite() {

        FileOutputStream fileOutputStream = null;

        String string = "hello world !!!";
        byte[] bytes = string.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put(bytes);
        // todo 一定要clear下，不然写不进去数据，因为这个时候position是15了，
        // 下面write方法写的时候从position算起，所以必须要用clear()方法将position置为0
        buffer.clear();


        try {

            Long current = System.currentTimeMillis();

            fileOutputStream = new FileOutputStream(writefilepath);
//            FileChannel fileChannel = fileOutputStream.getChannel();
//
//            int length = -1;
//            while ((length = fileChannel.write(buffer))!= 0){
//                System.out.print("每次写入长度：" + length + "\n");
//            }
//            fileChannel.close();


            fileOutputStream.write(bytes);

            System.out.print("写文件共花去了时间是： " + String.valueOf(System.currentTimeMillis() - current) + "ms");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {

        testRead0();

        testWrite();

        testRead();

    }


}
