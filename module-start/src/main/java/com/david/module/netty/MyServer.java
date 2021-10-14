package com.david.module.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import static io.netty.buffer.Unpooled.copiedBuffer;


/**
 * @author Sean Wu
 */
public class MyServer {

    public static void main(String[] args) throws Exception {

        //创建两个线程组 boosGroup、workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    //指定NIO模式为Server模式
                    .channel(NioServerSocketChannel.class)
                    //设置tcp缓存区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置发送缓存区大小
                    .option(ChannelOption.SO_SNDBUF, 10*1024)
                    //设置接收缓存区大小
                    .option(ChannelOption.SO_RCVBUF, 10*1024)
                    //是否保持连接，默认true
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();

                            p.addLast(new SimpleProtocolDecoder());
                            //给pipeline管道设置处理器
                            p.addLast(new MyServerHandler());

                            // 定长
                            //  p.addFirst(new FixedLengthFrameDecoder(4));

                            // 特殊字符
//                            DelimiterBasedFrameDecoder delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(20, copiedBuffer("\n".getBytes()));
//                            p.addFirst(delimiterBasedFrameDecoder);

                            // 自定义协议
                           // p.addFirst(new SimpleProtocolEncoder());
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("java技术爱好者的服务端已经准备就绪...");
            //绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 自定义的Handler需要继承Netty规定好的HandlerAdapter，
     * 才能被Netty框架所关联，有点类似SpringMVC的适配器模式
     */
    static class MyServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //获取客户端发送过来的消息
//            ByteBuf byteBuf = (ByteBuf) msg;
//            String sendedMessage = byteBuf.toString(CharsetUtil.UTF_8);

            SimpleProtocol simpleData = (SimpleProtocol) msg;
            System.out.println("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            //发送消息给客户端
            String respMsg = "{\"code\":\"iot.messagebroker.MessageContentIsNotBase64Encode\",\"errorMessage\":\"The message content is not encoded into the base64 format.\",\"requestId\":\"F20AB338-062C-591B-A48A-B21C8B846F47\",\"success\":false}";
            ctx.writeAndFlush(Unpooled.copiedBuffer(respMsg, CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //发生异常，关闭通道
            ctx.close();
        }
    }
}
