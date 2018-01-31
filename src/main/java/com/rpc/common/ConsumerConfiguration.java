package com.rpc.common;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.rpc.loadbalancer.impl.RandomLoadBalancer;
import com.rpc.proxy.DynamicProxy;
import com.rpc.transport.impl.RpcClientImpl;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午11:11:34 
  * @version：   1.0.0
  * @describe: 后置工厂Bean   
  */
public class ConsumerConfiguration implements BeanFactoryPostProcessor{
	/**扫描接口Map*/
	private Map<String, Class>interfaceScanner;
	/**客户端*/
	private String rpcClientType;
	/**故障策略*/
	private String strategyType;
	/**负载均衡策略*/
	private String loadBalancerType;
	/**注册中心*/
	private String registryType;
	public void setInterfaceScanner(Map<String, Class> interfaceScanner) {
		this.interfaceScanner = interfaceScanner;
	}
	public void setRpcClientType(String rpcClientType) {
		this.rpcClientType = rpcClientType;
	}
	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}
	public void setLoadBalancerType(String loadBalancerType) {
		this.loadBalancerType = loadBalancerType;
	}
	public void setRegistryType(String registryType) {
		this.registryType = registryType;
	}
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;
		//注入RpcClient
		BeanDefinitionBuilder rpcClient = BeanDefinitionBuilder.genericBeanDefinition(rpcClientType);
		rpcClient.setInitMethodName("init");
		rpcClient.setDestroyMethodName("close");
		defaultListableBeanFactory.registerBeanDefinition("rpcClient", rpcClient.getBeanDefinition());
		//注入LoadBalancer
		BeanDefinitionBuilder loadBalancer = BeanDefinitionBuilder.genericBeanDefinition(loadBalancerType);
		defaultListableBeanFactory.registerBeanDefinition("loadBalancer", loadBalancer.getBeanDefinition());
		//将RpcClient和LoadBalancer注入Strategy
		BeanDefinitionBuilder strategy = BeanDefinitionBuilder.genericBeanDefinition(strategyType);
		strategy.addConstructorArgReference("loadBalancer");
		strategy.addConstructorArgReference("rpcClient");
		defaultListableBeanFactory.registerBeanDefinition("strategy", strategy.getBeanDefinition());
		//注入Registry
		/*BeanDefinitionBuilder registry = BeanDefinitionBuilder.genericBeanDefinition(registryType);
		defaultListableBeanFactory.registerBeanDefinition("registry", registry.getBeanDefinition());*/
		//将interfaceScanner Strategy Registry 注入DynamicProxy中
		for (String targetInterface : interfaceScanner.keySet()) {
			BeanDefinitionBuilder proxy = BeanDefinitionBuilder.genericBeanDefinition(DynamicProxy.class);
			Class targetClass = interfaceScanner.get(targetInterface);
			proxy.addConstructorArgReference("strategy");
			proxy.addConstructorArgValue(targetClass);
			proxy.addConstructorArgReference(registryType);
			defaultListableBeanFactory.registerBeanDefinition(targetInterface, proxy.getBeanDefinition());
		}
	}
}
