package com.rpc.loadbalancer;

import java.util.List;

import com.rpc.common.HostAndPort;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午9:09:55 
  * @version：   1.0.0
  * @describe:    负载均衡接口
  */
public interface LoadBalancer {
	HostAndPort select(List<HostAndPort> list);
}
