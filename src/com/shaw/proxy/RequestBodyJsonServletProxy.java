package com.shaw.proxy;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;
import com.shaw.model.ControllerModel;
import com.shaw.pub.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
public class RequestBodyJsonServletProxy extends HttpServlet {

    private Class clazz;
    private Method method;

    private static Logger logger = LoggerFactory.getLogger(RequestBodyJsonServletProxy.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        postRequest(req, resp);

        ControllerModel controllerModel = Constants.controllerMapper.get(req.getServletPath());
        this.clazz = controllerModel.getClazz();
        this.method = controllerModel.getMethod();

        String targetPage = null;

        //获取函数返回数据类型
        //Type returnType = method.getGenericReturnType();

        //通过静态代理来初始化对象
        Object object = null;
        try {
            object= clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //通过静态代理来调用函数
        if (object != null) {
            try {
                targetPage = (String) method.invoke(object, req, resp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        preRequest(req, resp);
        req.getRequestDispatcher(targetPage).forward(req, resp);
        super.doPost(req, resp);
    }

    /**
     *  真实角色操作前的附加操作
     */
    private void postRequest(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub

    }

    /**
     *  真实角色操作后的附加操作
     */
    private void preRequest(HttpServletRequest req, HttpServletResponse resp) {
        // TODO Auto-generated method stub

    }

}
