package com.david.module.util.vendors;

import java.io.*;

/**
 * 关于流的util
 */
public class StreamUtil {

    public static Object readObjectFromFile(String filePath, String oClass) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // todo: fix it later
            //  Class<T> clazz = Class.forName(oClass);

            Object object = ois.readObject();

            ois.close();
            return object;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }

        return null;
    }
}
