package com.david.module.util.javas.concurrent;

import java.util.concurrent.*;

/**
 * 完成任务的servicw
 */
public class CompletionService2 {


    // 解耦 任务的提交和任务的完成
    static void testCompletionService() {

        // 执行任务的线程池
        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();

        // 管理着一个完成任务的queue manages an internal completion queue
        CompletionService completionService = new ExecutorCompletionService(threadPoolExecutor);

        String result = "任务结束，返回的是我！！";
        // Future 描述了一项未来可能完成的任务
        // 任务也是有生命周期的
        Future<String> futureTask = completionService.submit(() -> {
            System.out.print("我在运行！！！\n");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);

        // 取消任务，仅仅是把对应的thread的interupt标志位设置下
        /**
         * 注意，这种情况下cancel任务，会抛CancellationException 而不一定是 thread.InterruptedException
         * 因为此时futureTask的执行线程runner有可能还是null
         */
        boolean couldCancel = futureTask.cancel(true);
        System.out.print("任务可以被取消" + couldCancel);

        try {
            System.out.print("阻塞前?\n");

            // 当futureTask被cancel掉后，会抛出CancellationException异常
            String result3 = futureTask.get();

            System.out.print("阻塞后\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            /**
             * 此种情况下，异常会抛到这里
             */
            e.printStackTrace();
        }

    }

    // 解耦 任务的提交和任务的完成
    static void testCompletionService2() {

        // 执行任务的线程池
        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();

        // 管理着一个完成任务的queue
        CompletionService completionService = new ExecutorCompletionService(threadPoolExecutor);

        String result = "任务结束，返回的是我！！";
        // Future 描述了一项未来可能完成的任务
        // 任务也是有生命周期的
        Future<String> futureTask = completionService.submit(() -> {
            System.out.print("我在运行！！！\n");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 当futureTask被cancel掉后，会将运行的线程interupt，线程抛出InterruptedException异常
                e.printStackTrace();
            }
        }, result);


        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // 当futureTask被cancel掉后，会将运行的线程interupt，线程抛出InterruptedException异常
            e.printStackTrace();
        }
        /**
         * 注意，这种情况下cancel任务，会抛出thread.InterruptedException
         * 因为休眠了1ms后，futureTask的执行线程runner已经有数据，不再是null
         */
        // 取消任务，仅仅是把对应的thread的interupt标志位设置下
        boolean couldCancel = futureTask.cancel(true);
        System.out.print("任务可以被取消" + couldCancel);

        try {
            System.out.print("阻塞前?\n");

            // 当futureTask被cancel掉后，会抛出CancellationException异常
            String result3 = futureTask.get();

            System.out.print("阻塞后\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            /**
             * 此种情况下，异常不会抛到这里
             */
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        testCompletionService();
        testCompletionService2();

    }
}
