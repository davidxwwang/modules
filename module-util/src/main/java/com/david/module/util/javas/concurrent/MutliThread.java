package com.david.module.util.javas.concurrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

public class MutliThread {

    private ServerSocket socket;

    private static final Executor exe = Executors.newFixedThreadPool(10);


    void xx() {
        try {
            socket = new ServerSocket(80);
            while (true) {
                final Socket connect = socket.accept();
                Runnable task = new Runnable() {
                    public void run() {
                        handlerequest(connect);
                    }
                };
                new Thread(task).start();
                exe.execute(task);
            }

        } catch (IOException ex) {

        }
    }

    void handlerequest(Socket socket) {

    }

    public static void main(String[] args) {


//
//        ExecutorService pool1=Executors.newSingleThreadExecutor();
//
//        MyThread t1 = new MyThread();
//        MyThread t2 = new MyThread();
//        MyThread t3 = new MyThread();
//        MyThread t4 = new MyThread();
//        MyThread t5 = new MyThread();
//
//        pool1.execute(t1);
//        pool1.execute(t2);
//        pool1.execute(t3);
//        pool1.execute(t4);
//        pool1.execute(t5);
//
//        if (!pool1.isShutdown()){
//            pool1.shutdown();
//        }
//
//        ExecutorService pool2=Executors.newFixedThreadPool(2);
//
//        pool2.execute(t1);
//        pool2.execute(t2);
//        pool2.execute(t3);
//        pool2.execute(t4);
//        pool2.execute(t5);
//        if (!pool2.isShutdown()){
//            pool2.shutdown();
//        }
//

//        ExecutorService  service = new ThreadPoolExecutor(1,
//                2,
//                60L,
//                TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>(),  //ArrayBlockingQueue
//                new CustomThreadFactory(),
//                new CustomRejectedExecutionHandler()));


    }
}
