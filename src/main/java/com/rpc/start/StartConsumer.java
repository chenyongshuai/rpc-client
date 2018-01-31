package com.rpc.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpc.common.RpcContext;
import com.rpc.service.HelloWorldService;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月30日 上午10:46:04 
  * @version：   1.0.0
  * @describe:    
  */
public class StartConsumer {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("consumer2.xml");
		HelloWorldService helloWorldService = (HelloWorldService) ctx.getBean("helloWorldService");
		RpcContext.getContext().setAttribute("this is a secret message", "%*%^&%&^%*#*(*&*(#(*");
		String string = helloWorldService.sayHi("aaa");
		System.out.println(string);
	}
}
