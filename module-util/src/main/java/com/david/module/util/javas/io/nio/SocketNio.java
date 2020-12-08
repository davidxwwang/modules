package com.david.module.util.javas.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 关于nio https://blog.csdn.net/forezp/article/details/88414741介绍的比较详细
 */
public class SocketNio {

    static int PORT = 8081;

    static long TIMEOUT = 0;

    static final int BUF_SIZE = 1024;


    /**************** io 服务器 ****************/

    public static void client() {
        ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", PORT));
            if (socketChannel.finishConnect()) {
                int i = 0;
                while (true) {


                    String info = "I'm " + i++ + "-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }

                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (IOException | InterruptedException | BufferOverflowException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void server() {
        ServerSocket serverSocket = null;
        InputStream in = null;
        try {
            serverSocket = new ServerSocket(PORT);
            int recvMsgSize = 0;
            byte[] recvBuf = new byte[1024];

            while (true) {
                // todo 如果没有数据过来，就会阻塞到这里，下面的方法都不能执行了
                Socket clntSocket = serverSocket.accept();

                SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
                System.out.println("Handling client at " + clientAddress);
                in = clntSocket.getInputStream();
                while ((recvMsgSize = in.read(recvBuf)) != -1) {
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);

                    System.out.print("收到客户端的数据：");
                    System.out.println(new String(temp) + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**************************** NIO 服务器 *************************/

    /**
     * nio服务器
     */
    public static void nioSelector() {

        // 类似 多路选择器
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try {
            selector = Selector.open();
            // 配置 server-socket channel对应的socket的端口
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            // 把server-socket channel 注册到 多路选择器上，并表示该channel对 OP_ACCEPT事件感兴趣。
            // 多路选择器
            //      detects that the corresponding server-socket channel is ready to accept
            //      another connection
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 需要不停的去问"多路选择器"，哪个channel上IO事件来了，所以是同步的IO，而不是异步
                // selector方法是个block方法，当有一个chanel可io操作，就返回
                if (selector.select(TIMEOUT) == 0) {
                    System.out.println("==");
                    continue;
                }

                // 不会block
//                if(selector.selectNow() == 0){
//                    continue;
//                }

                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }

                    if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }

                    if (key.isWritable() && key.isValid()) {
                        handleWrite(key);
                    }

                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (ssc != null) {
                    ssc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();

        // ssChannel的socket端口是8081，这句话得到与8081端口连接的客户端的channel
        SocketChannel clientChannel = ssChannel.accept();

        // 配置客户端的channel，并配置 多路选择器对 "客户端channel io可读"事件感兴趣！！
        clientChannel.configureBlocking(false);
        clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    // 处理客户端channel可读事件
    // 可以在这里开启线程池，处理各个channel来的数据
    public static void handleRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        System.out.print("客户端channel信息：" + sc.toString());

        ByteBuffer buf = (ByteBuffer) key.attachment();


        long bytesRead = sc.read(buf);
        while (bytesRead > 0) {
            buf.flip();

            while (buf.hasRemaining()) {

                // 每次只能读一个char类型的数据
                char xx = (char) buf.get();
                System.out.print(xx);
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if (bytesRead == -1) {
            sc.close();
        }
    }

    public static void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            sc.write(buf);
        }
        buf.compact();
    }


    public static void main(String[] args) {

        new Thread(() -> {
            //server();
            nioSelector();
        }).start();

        client();

    }


}
