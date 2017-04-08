package com.shaw.model;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
public class ControllerModel {

    private Class clazz;
    private Method method;

    public ControllerModel(Class clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
