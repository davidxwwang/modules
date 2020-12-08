package com.david.module.util.javas.JVMBench;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) {
        new MyClassLoader().test();
    }

    void test() {
        doTestAllcation();
        // doFuturnTask();
        //doJVMTest();
    }

    void doJVMTest() {
        try {
            Class<?> className = findClass("file:/Users/david/Desktop/JavaWorkSpace/demo/MyJavaJVM/src/main/java/com/example/demo/Dog.class");
//            Field[] fields = className.getDeclaredFields();
//            String xx =  className.getName();
            System.out.print(className);
            try {
                Object object = className.newInstance();

                System.out.print(object);

                Field[] fields = className.getDeclaredFields();
                for (Field field : fields) {
                    Class<?> fieldClass = field.getType();
                    if (fieldClass.isAssignableFrom(String.class)) {
                        String fieldName = field.getName();
                        //   ObjectUtil.setFieldValueByName(object,fieldName, "😊😊");

                    }

                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void doFuturnTask() {

        FutureTask futureTask = new FutureTask(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);

                // 当设置了interrupt 下面join()和wait()会抛异常
                Thread.currentThread().interrupt();
                boolean interrupt = Thread.currentThread().isInterrupted();

                boolean interrupt2 = Thread.currentThread().isInterrupted();
                try {
                    Thread.currentThread().join();
                    Thread.currentThread().wait();
                    Thread.sleep(100);
                } catch (Exception ex) {
                    System.out.print(ex);
                }


                return "😊喜乐😢";

                // go ExecutionException
                //throw new Exception("test",null);

                // get normal result
                // return "😊喜乐😢";
            }
        });

        int xnnx = System.identityHashCode(futureTask);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<String> future = (Future<String>) executor.submit(futureTask);
        ExecutorService executor2 = executor;

        try {
            String xx = (String) futureTask.get();
            System.out.print("异步任务被blocked");
            System.out.print(xx);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            // Callable中抛出异常，程序会跑到这里
            e.printStackTrace();
        }

    }

    void doCompletionTask() {

        // 下面的Callable任务在executor2中执行
        ThreadPoolExecutor executor2 = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        CompletionService<String> completionService = new ExecutorCompletionService<String>(executor2);
        completionService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);

                // go ExecutionException
                Thread.currentThread().interrupt();
                boolean interrupt = Thread.currentThread().isInterrupted();
                return "😊喜乐😢";

                // go ExecutionException
                //throw new Exception("test",null);

                // get normal result
                // return "😊喜乐😢";
            }
        });

        try {
            Future<String> future = completionService.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void doTestAllcation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        int __1MB = 1024 * 1024;

//        allocation1 = new byte[2*__1MB];
//        allocation2 = new byte[2*__1MB];
//        allocation3 = new byte[2*__1MB];
        allocation4 = new byte[4 * __1MB]; // Minor GC

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String classPath = name;

        byte[] classBytes = null;
        Path path = null;
        try {
            path = Paths.get(new URI(classPath));
            classBytes = Files.readAllBytes(path);
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }

        Class xx = defineClass(null, classBytes, 0, classBytes.length);
        return xx;
    }
}
