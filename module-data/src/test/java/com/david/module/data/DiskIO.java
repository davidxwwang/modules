package com.david.module.data;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class DiskIO {

    @Test
    public void doTestDiskIO(){

        Long now = System.currentTimeMillis();
        String path =  "/Users/david/Documents/settings.xml";
        String path1 = "/Users/david/Documents/squentialAcess.log";
        String path2 = "/Users/david/Documents/randomAcess.log";
     //   fileRead("/Users/david/Documents/module-start-0.0.1-SNAPSHOT.jar");
        Long spentTime = (System.currentTimeMillis() - now) ;

        fileWrite(path1,"hello.word");



        String str = squentailFileRead(path1);
      //  fileRead(path1);
        System.out.print("");

    }

    public static void fileRead(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                //创建FileInputStream对象，读取文件内容
                FileInputStream fis = new FileInputStream(file);
                byte[] bys = new byte[1024];
                while ( fis.read(bys, 0, bys.length) != -1) {
                    //将字节数组转换为字符串
                    String txt = new String(bys, StandardCharsets.UTF_8);
                    System.out.print(txt);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static void fileWrite(String filePath, String content) {
        FileOutputStream outputStream = null;
        try {
            File file = new File(filePath);
            boolean isCreate = file.createNewFile();//创建文件
            if (isCreate) {
                outputStream = new FileOutputStream(file);//形参里面可追加true参数，表示在原有文件末尾追加信息
                outputStream.write(content.getBytes());
            }else {
                outputStream = new FileOutputStream(file,true);//表示在原有文件末尾追加信息
                outputStream.write(content.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static long fileWrite(String filePath, String content, int index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 1024 * 1024 * 1024);
            map.position(index);
            map.put(content.getBytes());
            return map.position();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0L;
    }

    public static String squentailFileRead(String filePath) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer memory_mapped_file_buffer;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
//            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, offset, pageSize);
//            byte[] byteArr = new byte[(int) pageSize];
//            map.get(byteArr, 0, (int) pageSize);

            int pageSize = 128;
            Long offset = 0L;
            int byteArrayLength = 0;
            StringBuilder stringBuilder = new StringBuilder();
            do {
                memory_mapped_file_buffer = targetFileChannel.map(FileChannel.MapMode.READ_ONLY, offset, pageSize);
                byte[] byteArr = new byte[(int) pageSize];

                ByteBuffer byteBuffer = memory_mapped_file_buffer.get(byteArr, 0, pageSize);
                int limit = byteBuffer.limit();

                byteArrayLength = byteArr.length;

                stringBuilder.append(new String(byteArr));

                offset += pageSize;

            }while (byteArrayLength >= pageSize);
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }
}
