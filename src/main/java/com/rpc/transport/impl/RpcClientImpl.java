package com.rpc.transport.impl;

import com.rpc.common.HostAndPort;
import com.rpc.protocol.MethodInvokeMetaWarp;
import com.rpc.protocol.ResultWarp;
import com.rpc.serializer.ObjectDecoder;
import com.rpc.serializer.ObjectEncoder;
import com.rpc.transport.RpcClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月29日 下午1:30:48 
  * @version：   1.0.0
  * @describe:    
  */
public class RpcClientImpl implements RpcClient{
	/**初始化引导类*/
	private Bootstrap sb;
	/**线程池*/
	private NioEventLoopGroup worker;
	public void init() {
		// 1.创建程序引导类
        sb = new Bootstrap();
        // 2.创建线程池
        worker = new NioEventLoopGroup();
        // 3.将引导类与线程池关联
        sb.group(worker);
        // 4.创建  管道
        sb.channel(NioSocketChannel.class);
	}
	/**
	 * 初始化通道调用服务
	 */
	public ResultWarp invoke(final MethodInvokeMetaWarp mimw, HostAndPort hostAndPort) {
		// 5.初始化通道
		sb.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				// 5.1获取管道
				ChannelPipeline pipeline = socketChannel.pipeline();
				// 5.2解帧
                pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                // 5.3解码
                pipeline.addLast(new ObjectDecoder());
                // 5.4加帧头
                pipeline.addLast(new LengthFieldPrepender(2));
                // 5.5编码
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ChannelHandlerAdapter(){
                	/**
                	 * 捕获异常
                	 */
                	@Override
                	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                		System.out.println("Exception(客户端):  "+cause.toString());
                	}
                	/**
                	 * 将数据参数传递给服务端
                	 */
                	@Override
                	public void channelActive(ChannelHandlerContext ctx) throws Exception {
                		ChannelFuture channelFuture = ctx.writeAndFlush(mimw);
                        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                	}
                	/**
                	 * 读取服务端调用结果
                	 */
                	@Override
                	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                		System.out.println("Receive Message(客户端收到消息):   "+msg);
                		ResultWarp resultWarp = (ResultWarp) msg;
                		mimw.setResultWarp(resultWarp);
                	}
                });
			}
		});
		try {
            ChannelFuture channelFuture = sb.connect("localhost",9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mimw.getResultWarp();
	}
	/**
	 * 释放资源
	 */
	public void close() {
		if(worker!=null){
            worker.shutdownGracefully();
        }
	}
}
