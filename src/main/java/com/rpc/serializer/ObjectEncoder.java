package com.rpc.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Max Chen on 2017/11/28 17:26.
 */
public class ObjectEncoder extends MessageToMessageEncoder<Object> {
    private Serializer serializer = new JdkSerializer();

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
    /**
     * 编码
     */
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, List<Object> out) throws Exception {
        byte[] bytes = serializer.serialize(msg);
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(bytes);
        out.add(byteBuf);
    }
}
