package com.rpc.loadbalancer.impl;

import java.util.List;
import java.util.Random;

import com.rpc.common.HostAndPort;
import com.rpc.loadbalancer.LoadBalancer;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午9:11:58 
  * @version：   1.0.0
  * @describe:    随机策略   获取IP:port
  */
public class RandomLoadBalancer implements LoadBalancer{

	public HostAndPort select(List<HostAndPort> list) {
		return list.get(new Random().nextInt(list.size()));
	}

}
