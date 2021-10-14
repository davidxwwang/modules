package com.david.module.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器 头 + lenght + content
 */
public class SimpleProtocolEncoder extends MessageToByteEncoder<SimpleProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SimpleProtocol msg, ByteBuf out) throws Exception {

        out.writeInt(SimpleProtocol.PROTOCOL_HEAD)
                .writeInt(msg.getLength())
                .writeBytes(msg.getContent());
    }
}
