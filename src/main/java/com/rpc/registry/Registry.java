package com.rpc.registry;


import java.io.IOException;
import java.util.List;

import com.rpc.common.HostAndPort;

/**
 * Created by Max Chen on 2017/12/1 14:16.
 * 注册中心接口  zookeeper  redis....
 *
 */
public interface Registry {
    public static final String PREFIX = "/RPC";
    public static final String SUFFIX = "/providers";
    /**
     * 查找节点
     * @param targetClass
     * @return
     */
    public List<HostAndPort> searchService(Class targetClass);
    /**
     * 注册节点
     * @param targetClass
     * @param hostAndPort
     */
    public void registerService(Class targetClass, HostAndPort hostAndPort);
    /**
     * 订阅节点
     * @param targetClass
     * @param hostAndPorts
     * @return
     * @throws IOException
     */
    public List<HostAndPort> subscrible(Class targetClass, List<HostAndPort> hostAndPorts) throws IOException;
    /**
     * 删除节点
     * @param targetClass
     * @param hostAndPort
     */
    public void deleteNode(Class targetClass, HostAndPort hostAndPort);
    /**
     * 释放资源
     */
    public void close();
}
