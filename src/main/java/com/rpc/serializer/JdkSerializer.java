package com.rpc.serializer;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/** @author:  v_chenyongshuai@:
  * @date:  2018年1月29日 下午1:01:49 
  * @version：   1.0.1
  * @describe:    序列化实现类
  */
public class JdkSerializer implements Serializer{
	/**
	 * 序列化
	 */
	public byte[] serialize(Object object) {
		return SerializationUtils.serialize((Serializable)object);
	}
	/**
	 * 反序列化
	 */
	public Object deserialize(byte[] bytes) {
		return SerializationUtils.deserialize(bytes);
	}
}
