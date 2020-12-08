package com.david.module.util.javas.concurrent;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// http://www.cnblogs.com/zedosu/p/6665306.html

public class CustomThreadPoolExecutor {

    private ThreadPoolExecutor pool = null;

    // 如果使用Integer，就会乱套了！！！！
    private AtomicInteger finished = new AtomicInteger(0);

    private Integer unSafeFinished = 0;


    /**
     * 线程池初始化方法
     *
     * corePoolSize 核心线程池大小----10
     * maximumPoolSize 最大线程池大小----30
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(5)====5容量的阻塞队列
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
     * 							即当提交第26个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
     * 						          任务会交给RejectedExecutionHandler来处理
     */

    /**
     * 方法中建立一个核心线程数为10个，缓冲队列容量为5个线程任务，
     * 执行时会先睡眠3秒，保证提交10任务时，线程数目被占用完，
     * 再提交5个任务时，会把5个任务加到阻塞队列
     * 再提交20个任务时，线程池新增20个线程，这时到了30（maximumPoolSize），自此以后提交的任务都被拒绝了
     * 会交给CustomRejectedExecutionHandler 异常处理类来处理。
     */
    public void init() {
        /**
         * maximumPoolSize被设置成了30，说明最多只能生成30个线程
         * 如果每个任务执行的时间都够久，那么从第36个任务开始，就会被拒绝（30个最大线程 + 5个queue的容量）
         *      当然，先生成10个线程，再入5个ArrayBlockingQueue，再生成20个线程
         */
        pool = new MyCustomThreadPoolExcutor(
                10,
                30,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());


    }


    public List<Runnable> shutdownNow() {
        if (pool != null) {
            return pool.shutdownNow();
        }
        return null;
    }


    public ExecutorService getCustomThreadPoolExecutor() {
        return this.pool;
    }

    private class MyCustomThreadPoolExcutor extends ThreadPoolExecutor {

        public MyCustomThreadPoolExcutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }


        /**
         * hook方法
         * @return
         */
        /*************************************************************/
        /*************************************************************/
        /*************************************************************/

        // hook 方法
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            int safeCount = finished.addAndGet(1);
            unSafeFinished++;


            System.out.print("\n线程安全计数器计数现在有" + finished.get() + "个任务执行结束" + "\n线程不安全计数器现在有" + unSafeFinished + "个任务执行结束\n");

            int delter = unSafeFinished - safeCount;
            if (delter != 0) {
                System.out.print("两个计数器相差" + delter + "\n");
            }
            System.out.print("\n");

            MyTask task = (MyTask) r;
            String taskId = task.getTaskId();
            System.out.print("\n" + taskId + "已经执行结束\n");

            super.afterExecute(r, t);
        }

        @Override
        public List<Runnable> shutdownNow() {

            System.out.print("shutdownNow");
            return super.shutdownNow();
        }


        @Override
        protected void beforeExecute(Thread t, Runnable r) {

            MyTask task = (MyTask) r;
            String taskId = task.getTaskId();
            System.out.print("\n" + taskId + "即将开始执行\n");

            super.beforeExecute(t, r);
        }


        @Override
        protected void terminated() {

            System.out.print("所有任务结束！！");
            super.terminated();
        }

        /*************************************************************/
        /*************************************************************/
    }

    private class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = "第** " + count.addAndGet(1) + " **号线程";
            System.out.println("新生成了第 " + count.get() + "个 线程，名称为：" + threadName);
            t.setName(threadName);
            return t;
        }
    }


    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        private AtomicInteger rejectCount = new AtomicInteger(0);

        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("有" + rejectCount.addAndGet(1) + "个任务被拒绝");
            // 记录异常
            // 报警处理等

            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法
                BlockingQueue<Runnable> queue = executor.getQueue();

                // 一直会阻塞queue，直到有空间插入为止
                executor.getQueue().put(r);
                System.out.print("l am here !\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        int x = "".hashCode();

        CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();
        // 1.初始化
        exec.init();

        ExecutorService pool = exec.getCustomThreadPoolExecutor();
        for (int i = 1; i < 3; i++) {
            MyTask task = new MyTask();
            task.setTaskId("第" + i + "号任务");
            pool.execute(task);

        }
        //  pool.shutdownNow();

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) exec.getCustomThreadPoolExecutor();
        System.out.print("\n统计下数据" + new Date());

        // todo 为什么这个方法被block了,这里要好好的理解下 为什么获得是68/67，我非常的不能理解
        long finished = threadPoolExecutor.getCompletedTaskCount();
        System.out.print("\n共完成了" + finished + "个任务 " + new Date());

        //2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了，结束了
        // List<Runnable> unFinishedWOrks = exec.shutdownNow();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Runnable> unFinishedWorks = exec.shutdownNow();

        long finished2 = threadPoolExecutor.getCompletedTaskCount();
        int count = threadPoolExecutor.getActiveCount();
        BlockingQueue<Runnable> xx = threadPoolExecutor.getQueue();


        while (true) ;

    }
}

