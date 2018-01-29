package com.rpc.protocol;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Max Chen on 2017/11/29 17:33.‘
 *
 *  方法调用  实体类  +  附件
 *
 */
public class MethodInvokeMetaWarp implements Serializable {

    private MethodInvokeMeta methodInvokeMeta;
    private Map<Object,Object>attachment;
    private ResultWarp resultWarp;

    public MethodInvokeMetaWarp() {
    }

    public MethodInvokeMetaWarp(MethodInvokeMeta methodInvokeMeta) {
        this.methodInvokeMeta = methodInvokeMeta;
    }

    public MethodInvokeMetaWarp(MethodInvokeMeta methodInvokeMeta, Map<Object, Object> attachment) {
        this.methodInvokeMeta = methodInvokeMeta;
        this.attachment = attachment;
    }

    public MethodInvokeMeta getMethodInvokeMeta() {
        return methodInvokeMeta;
    }

    public void setMethodInvokeMeta(MethodInvokeMeta methodInvokeMeta) {
        this.methodInvokeMeta = methodInvokeMeta;
    }

    public Map<Object, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<Object, Object> attachment) {
        this.attachment = attachment;
    }

    public ResultWarp getResultWarp() {
        return resultWarp;
    }

    public void setResultWarp(ResultWarp resultWarp) {
        this.resultWarp = resultWarp;
    }

    @Override
    public String toString() {
        return "MethodInvokeMetaWarp{" +
                "methodInvokeMeta=" + methodInvokeMeta +
                ", attachment=" + attachment +
                ", resultWarp=" + resultWarp +
                '}';
    }
}
