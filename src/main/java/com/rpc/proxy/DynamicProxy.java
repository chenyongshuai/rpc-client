package com.rpc.proxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;

import com.rpc.common.HostAndPort;
import com.rpc.common.RpcContext;
import com.rpc.failover.Strategy;
import com.rpc.protocol.MethodInvokeMeta;
import com.rpc.protocol.MethodInvokeMetaWarp;
import com.rpc.protocol.ResultWarp;
import com.rpc.registry.Registry;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午10:02:54 
  * @version：   1.0.0
  * @describe:    动态代理类
  */
public class DynamicProxy implements InvocationHandler, FactoryBean{
	/**故障策略*/
	private Strategy strategy;
	/**目标接口*/
	private Class targetInterface;
	/**IP Port*/
    private List<HostAndPort>hostAndPorts;
    /**注册中心*/
    private Registry registry;
	public DynamicProxy(Strategy strategy, Class targetInterface, Registry registry) throws IOException {
		this.strategy = strategy;
		this.targetInterface = targetInterface;
		this.registry = registry;
		// 查询服务
		hostAndPorts = registry.searchService(targetInterface);
		// 订阅服务
		hostAndPorts = registry.subscrible(targetInterface, hostAndPorts);
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MethodInvokeMeta methodInvokeMeta = new MethodInvokeMeta(targetInterface, method.getName(), method.getParameterTypes(), args);
		MethodInvokeMetaWarp mimw = new MethodInvokeMetaWarp(methodInvokeMeta);
		// 将客户端附件信息设置入协议
		mimw.setAttachment(RpcContext.getContext().getAllAttributes());
		ResultWarp resultWarp = strategy.invoke(mimw, hostAndPorts);
		// 将客户端附件信息和服务端附件信息合并
		RpcContext.getContext().getAllAttributes().putAll(resultWarp.getAttachment());
		return resultWarp.getResult().getResult();
	}
	public Object getObject() throws Exception {
		/**
		 * 1. 类加载器
		 * 2. 目标接口
		 * 3. 类
		 */
		return Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), new Class[]{targetInterface}, this);
	}
	public Class getObjectType() {
		return targetInterface;
	}
	public boolean isSingleton() {
		return true;
	}
	
}
