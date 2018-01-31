package com.rpc.registry.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.rpc.common.HostAndPort;
import com.rpc.registry.Registry;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月29日 下午4:20:43 
  * @version：   1.0.0
  * @describe:    
  */
public class ZooKeeperRegistry implements Registry{
	/**服务端口IP*/
	private static String server;
	/**ZK客户端对象*/
    private static ZkClient client;
    /**
     * 有参构造 创建ZkClient
     * @param server
     */
    public ZooKeeperRegistry(String server) {
        client = new ZkClient(server);
    }
    /**
     * 查看所有子节点
     */
	public List<HostAndPort> searchService(Class targetClass) {
		// 父节点
        String parentNode = PREFIX+SUFFIX+"/"+targetClass.getName();
        List<String> childrenList = client.getChildren(parentNode);
        List<HostAndPort>hostAndPorts = new CopyOnWriteArrayList<HostAndPort>();
        HostAndPort hostAndPort = null;
        for (String child : childrenList) {
			hostAndPort = new HostAndPort();
			String[] split = child.split(":");
			hostAndPort.setHost(split[0]);
			hostAndPort.setPort(Integer.parseInt(split[1]));
			hostAndPorts.add(hostAndPort);
		}
		return hostAndPorts;
	}
	/**
	 * 注册节点
	 */
	public void registerService(Class targetClass, HostAndPort hostAndPort) {
		String parentNode = PREFIX+SUFFIX+"/"+targetClass.getName();
		// 先判断父节点是否存在，如果不存在，则创建父节点
		if(!client.exists(parentNode)){
			client.createPersistent(parentNode, true);
		}
		// 注册服务
		String node = parentNode+"/"+hostAndPort.getHost()+":"+hostAndPort.getPort();
		if(client.exists(node)){
			client.deleteRecursive(node);
		}
		// 创建临时节点
		client.createEphemeral(node);
	}
	/**
	 * 订阅服务
	 */
	public List<HostAndPort> subscrible(Class targetClass, final List<HostAndPort> hostAndPorts) throws IOException {
		String parentNode = PREFIX+SUFFIX+"/"+targetClass.getName();
		System.out.println("====开始监测====");
		client.subscribeChildChanges(parentNode,new IZkChildListener() {
			public void handleChildChange(String arg0, List<String> currentChilds) throws Exception {
				hostAndPorts.clear();
				for (String child : currentChilds) {
					HostAndPort hostAndPort = new HostAndPort();
					String[] split = child.split(":");
					hostAndPort.setHost(split[0]);
					hostAndPort.setPort(Integer.parseInt(split[1]));
					hostAndPorts.add(hostAndPort);
				}
			}
		});
		return hostAndPorts;
	}
	/**
	 * 删除节点
	 */
	public void deleteNode(Class targetClass, HostAndPort hostAndPort) {
		String parentNode = PREFIX+SUFFIX+"/"+targetClass.getName();
		client.delete(parentNode+"/"+hostAndPort.getHost()+":"+hostAndPort.getPort());
	}

	public void close() {
		if(client!=null){
			client.close();			
		}
	}

}
