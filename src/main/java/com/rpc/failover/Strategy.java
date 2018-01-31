package com.rpc.failover;

import java.util.List;

import com.rpc.common.HostAndPort;
import com.rpc.protocol.MethodInvokeMetaWarp;
import com.rpc.protocol.ResultWarp;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午9:18:44 
  * @version：   1.0.0
  * @describe:    集群策略接口   故障转移   快速结束
  */
public interface Strategy {
	ResultWarp invoke(MethodInvokeMetaWarp mimw, List<HostAndPort>hostAndPorts) throws Exception;
}
