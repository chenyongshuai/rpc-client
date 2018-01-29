package com.rpc.transport;

import com.rpc.common.HostAndPort;
import com.rpc.protocol.MethodInvokeMetaWarp;
import com.rpc.protocol.ResultWarp;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月29日 下午1:19:56 
  * @version：   1.0.0
  * @describe:    
  */
public interface RpcClient {
	/**
	 * 初始化方法
	 */
	void init();
	/**
     * 调用  服务
     * 传递  参数  1. 协议  2.  服务参数
     */
	ResultWarp invoke(MethodInvokeMetaWarp mimw, HostAndPort hostAndPort);
    /**
     * 释放资源
     */
    void close();
}
