package com.david.module.netty;

import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpObjectAggregator;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpRequestDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;


/**
 * @author Sean Wu
 */
public class MyClient {

    public static void main(String[] args) throws Exception {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                // 指定channel是socketChannel
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        ch.pipeline().addLast(new SimpleProtocolEncoder());
                        ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new ClientHandler());
                            }
                        });

                        ChannelFuture f = b.connect("127.0.0.1", 6666).sync();
                        f.channel().closeFuture().sync();
                        //  group.shutdownGracefully();
                    }

                });
    }

    static class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                String[] msgs = new String[]{"AAAA", "BBBB"};
//                                ByteBuf message = null;
//                                for (int i = 0; i < 3000; i++) {
//                                    message = Unpooled.buffer(4);
//                                    message.writeBytes(msgs[i % 2].getBytes());
//                                    ctx.writeAndFlush(message);
//                                    System.out.println("send msg:" + msgs[i % 2]);
//                                }
//                                String msgs = "header\ndavid";
//                                ByteBuf message = null;
//                                message = Unpooled.buffer(20);
//                                message.writeBytes(msgs.getBytes());
//                                ctx.writeAndFlush(message);
//                                System.out.println("send msg:" + msgs);
            String[] msgs = new String[]{"a", "BB", "ccc", "DDDD"};
            for (int i = 0; i < 10; i++) {
                SimpleProtocol simpleData = new SimpleProtocol(msgs[i % 4].getBytes());
                ctx.writeAndFlush(simpleData);
                System.out.println("send msg:" + msgs[i % 4]);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            String sendedMessage = byteBuf.toString(CharsetUtil.UTF_8);
            System.out.print("收到服务器数据:" + sendedMessage);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("发送消息完成");
        }
    }
}