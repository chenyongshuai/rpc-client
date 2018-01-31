package com.rpc.failover.impl;

import java.util.List;

import com.rpc.common.HostAndPort;
import com.rpc.failover.Strategy;
import com.rpc.loadbalancer.LoadBalancer;
import com.rpc.protocol.MethodInvokeMetaWarp;
import com.rpc.protocol.ResultWarp;
import com.rpc.transport.RpcClient;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午9:23:31 
  * @version：   1.0.0
  * @describe:  快速调用
  */
public class FailFastStrategy implements Strategy{
	private LoadBalancer loadBalancer;
	private RpcClient rpcClient;
	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
	public void setRpcClient(RpcClient rpcClient) {
		this.rpcClient = rpcClient;
	}
	public FailFastStrategy() {
	}
	public FailFastStrategy(LoadBalancer loadBalancer, RpcClient rpcClient) {
		super();
		this.loadBalancer = loadBalancer;
		this.rpcClient = rpcClient;
	}
	public ResultWarp invoke(MethodInvokeMetaWarp mimw, List<HostAndPort> hostAndPorts) throws Exception {
		HostAndPort hostAndPort = loadBalancer.select(hostAndPorts);
		ResultWarp resultWarp = rpcClient.invoke(mimw, hostAndPort);
		if(resultWarp.getResult().getException()!=null){
			throw resultWarp.getResult().getException();
		}
		return resultWarp;
	}
}
