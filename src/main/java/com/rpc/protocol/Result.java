package com.rpc.protocol;

import java.io.Serializable;

/**
 * Created by Max Chen on 2017/11/29 17:36.
 *
 *   服务端  返回调用结果 实体类
 */
public class Result implements Serializable{

    private Object result;
    private Exception exception;

    public Result(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Result(Object result, Exception exception) {

        this.result = result;
        this.exception = exception;
    }

    public Result() {

    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", exception=" + exception +
                '}';
    }
}
