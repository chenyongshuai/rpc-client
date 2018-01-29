package com.rpc.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

/**
 * Created by Max Chen on 2017/11/28 17:31.
 */
public class ObjectDecoder extends MessageToMessageDecoder<ByteBuf> {
    private Serializer serializer = new JdkSerializer();


    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }
    /**
     * 解码
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        Object o = serializer.deserialize(bytes);
        out.add(o);
    }
}
