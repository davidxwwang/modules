package com.david.module.util.javas.concurrent;

import java.util.HashMap;
import java.util.concurrent.*;

public class Tasks {

    // 线程的基本测试
    static void testThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.State state0 = Thread.currentThread().getState();
                try {
                    Thread.sleep(100000);
                    Thread.State state1 = Thread.currentThread().getState();
                    System.out.print("线程被调起来了");
                } catch (InterruptedException e) {
                    // 调用thread.interrupt(); 会到这里
                    e.printStackTrace();
                }

                // ，线程是 RUNNABLE 状态
                Thread.State state = Thread.currentThread().getState();
                System.out.print("线程被调起来了");
            }
        });

        // 没调start()方法之前，线程是 NEW 状态
        Thread.State state = thread.getState();
        thread.start();

        thread.interrupt();


        Thread.State state2 = thread.getState();

        while (true) ;

    }

    static void taskDemo() {

        // todo 有 @FunctionalInterface标识的才可以使用lambda表达式
        // todo （Runnable）无返回值的任务
        Runnable runnable = () -> {
            System.out.print("xxx");
        };

        // todo （Callable）有返回值的任务
        Callable<Integer> callable = () -> {
            System.out.print("cccc");
            return 1;
        };
        try {
            Integer xx = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Callable callable2 = new Callable() {
            @Override
            public Object call() throws Exception {
                return 1;
            }
        };
        try {
            callable2.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void testFuturnTask() {

        // todo 异步任务
        FutureTask<Integer> asynTask = new FutureTask<>(() -> {

            System.out.print("提交的任务: " + System.currentTimeMillis());
            // 这个可以是一个网络请求/ IO文件读取，获得数据后返回
            Thread.sleep(1000, 0);
            System.out.print("提交的任务结束: " + System.currentTimeMillis());
            return 1;
        });


    }


    static void testAsynTask() {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<String> callTask = () -> {
            try {
                // 这个可以是一个网络请求/ IO文件读取，获得数据后返回
                Thread.sleep(5000, 0);
                return "callable task is called";

            } catch (InterruptedException e) {
                System.out.print("interrupted");
            }
            return null;

        };

        Future returnedCallableFutureTask = executorService.submit(callTask);
        boolean isOk = returnedCallableFutureTask.isDone();
        boolean isCancel = returnedCallableFutureTask.isCancelled();


        //  returnedCallableFutureTask.cancel(true);

        // ExecutorService关闭仅仅是设置线程的interrupt()
//        try {
//            executorService.shutdown();
//        }catch (InterruptedException ex){
//            System.out.print("interrupted");
//        }
        executorService.shutdown();
        // executorService.shutdownNow();

        System.out.print("异步调用前\n");
        try {
            String result = (String) returnedCallableFutureTask.get();
            System.out.print("得到的结果：" + result);

        } catch (CancellationException | InterruptedException | ExecutionException e) {
            //  if the computation was cancelled
            // the current thread was interrupted
            e.printStackTrace();
        }
        System.out.print("异步调用后\n");

//        try {
//            /************************************/
//            Future returnedFutureTask =  executorService.submit(asynTask);
//            // 为null 因为传进去的是runable，无法得到返回值，只有传进去的是Callable的，才能拿到返回值
//            Integer futureValue1 = (Integer) returnedFutureTask.get();
//
//            Integer futurnValue0 = asynTask.get();
//            /************************************/
//
//            System.out.print("异步任务执行结束，结果是: " + futureValue1);
//
//
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        boolean isCancelled = asynTask.isCancelled();
//        boolean isDone = asynTask.isDone();
//        asynTask.cancel(true);

        while (true) ;

    }


    public static void main(String[] args) {

        testAsynTask();

//        testThread();
//
//        taskDemo();

    }

}
