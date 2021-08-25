package com.david.module.help;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class IoReaderWriter {

    public static void fileRead(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                //创建FileReader对象，读取文件中的内容
                FileReader reader = new FileReader(file);
                char[] ch = new char[1];
                while (reader.read(ch) != -1) {
                    System.out.print(ch);
                }
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String fileRead(String filePath, long index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, index);
            byte[] byteArr = new byte[10 * 1024];
            map.get(byteArr, 0, (int) index);
            return new String(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }
}
