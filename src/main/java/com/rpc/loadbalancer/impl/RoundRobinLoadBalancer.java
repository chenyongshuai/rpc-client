package com.rpc.loadbalancer.impl;

import java.util.List;

import com.rpc.common.HostAndPort;
import com.rpc.loadbalancer.LoadBalancer;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午9:14:13 
  * @version：   1.0.0
  * @describe: 轮询策略   
  */
public class RoundRobinLoadBalancer implements LoadBalancer{
	int count = 0;
	public HostAndPort select(List<HostAndPort> list) {
		HostAndPort hostAndPort = list.get(count % list.size());
		count ++;
		if(count<0) count = 0;
		return hostAndPort;
	}

}
