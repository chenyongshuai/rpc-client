package com.rpc.protocol;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Max Chen on 2017/11/29 17:37.
 *
 *
 *   返回结果实体类  +  附件
 */
public class ResultWarp implements Serializable{
	/**
	 * 结果对象
	 */
    private Result result;
    /**
     * 附件
     */
    private Map<Object,Object> attachment;


    public ResultWarp() {
    }

    public ResultWarp(Result result) {
        this.result = result;
    }

    public ResultWarp(Result result, Map<Object, Object> attachment) {
        this.result = result;
        this.attachment = attachment;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<Object, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<Object, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "ResultWarp{" +
                "result=" + result +
                ", attachment=" + attachment +
                '}';
    }
}
