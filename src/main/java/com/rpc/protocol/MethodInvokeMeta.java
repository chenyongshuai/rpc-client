package com.rpc.protocol;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Max Chen on 2017/11/29 17:27.
 *
 *      方法调用信息  实体类
 */
public class MethodInvokeMeta implements Serializable{
    /**
     * 1.接口名
     * 2.方法名
     * 3.参数类型
     * 4.形参列表
     */
    private Class targetClass;
    private String methodName;
    private Class<?>[]parameterTypes;
    private Object[]parameters;

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "MethodInvokeMeta{" +
                "targetClass=" + targetClass +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }

    public MethodInvokeMeta(Class targetClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) {

        this.targetClass = targetClass;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public MethodInvokeMeta() {

    }
}
