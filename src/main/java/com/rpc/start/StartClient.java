package com.rpc.start;

import com.rpc.common.HostAndPort;
import com.rpc.protocol.MethodInvokeMeta;
import com.rpc.protocol.MethodInvokeMetaWarp;
import com.rpc.protocol.ResultWarp;
import com.rpc.service.HelloWorldService;
import com.rpc.transport.RpcClient;
import com.rpc.transport.impl.RpcClientImpl;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月29日 下午1:59:06 
  * @version：   1.0.0
  * @describe:    
  */
public class StartClient {
	public static void main(String[] args) {
		MethodInvokeMeta methodInvokeMeta = new MethodInvokeMeta();
		methodInvokeMeta.setMethodName("sayHi");
		methodInvokeMeta.setParameters(new Object[]{"张三"});
		methodInvokeMeta.setParameterTypes(new Class[]{String.class});
		methodInvokeMeta.setTargetClass(HelloWorldService.class);
		MethodInvokeMetaWarp mimw = new MethodInvokeMetaWarp();
		mimw.setMethodInvokeMeta(methodInvokeMeta);
		RpcClient rpc = new RpcClientImpl();
		rpc.init();
		ResultWarp resultWarp = rpc.invoke(mimw, new HostAndPort("localhost", 9999));
		Exception exception = resultWarp.getResult().getException();
		if(exception!=null){
			exception.printStackTrace();
			return;
		}
		Object object = resultWarp.getResult().getResult();
		System.out.println(object);
		rpc.close();
	}
}
