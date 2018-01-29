package com.rpc.serializer;
/** @author:  v_chenyongshuai@:
  * @date:  2018年1月29日 下午12:00:16 
  * @version：   1.0.1
  * @describe:    序列化接口
  */
public interface Serializer {
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	byte[] serialize(Object object);
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	Object deserialize(byte[] bytes);
}
