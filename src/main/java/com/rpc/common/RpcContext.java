package com.rpc.common;

import java.util.HashMap;
import java.util.Map;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 下午2:49:52 
  * @version：   1.0.0
  * @describe:   附件域
  */
public class RpcContext {
	/**ThreadLocal*/
	private static final ThreadLocal<RpcContext>RPC_CONTEXT_THREAD_LOCAL = new 	InheritableThreadLocal<RpcContext>();
	/**单例模式*/
	private RpcContext(){}
	/**作用域*/
	private Map<Object, Object>attribute = new HashMap<Object, Object>();
	/**
	 * 创建和获取RpcContext
	 * @return context
	 */
	public static synchronized RpcContext getContext(){
		RpcContext context = RPC_CONTEXT_THREAD_LOCAL.get();
		if(context == null){
			context = new RpcContext();
			RPC_CONTEXT_THREAD_LOCAL.set(context);
		}
		return context;
	}
	/**
	 * 向作用域中存值
	 * @param key
	 * @param value
	 */
	public void setAttribute(Object key, Object value){
		attribute.put(key, value);
	}
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public Object getAttribute(Object key){
		Object object = attribute.get(key);
		return object;
	}
	/**
	 * 获取作用域Map
	 * @return
	 */
	public Map<Object, Object> getAllAttributes(){
		return attribute;
	}
}
