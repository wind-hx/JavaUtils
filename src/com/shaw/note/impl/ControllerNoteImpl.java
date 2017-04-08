package com.shaw.note.impl;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;
import com.shaw.model.ControllerModel;
import com.shaw.note.Controller;
import com.shaw.note.RequestBody;
import com.shaw.note.RequestBodyJson;
import com.shaw.proxy.RequestBodyJsonServletProxy;
import com.shaw.proxy.RequestBodyServletProxy;
import com.shaw.pub.Constants;
import com.shaw.utils.NoteUtils;
import com.shaw.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
public class ControllerNoteImpl {

    //使用volatile关键字保其可见性
    volatile private static ControllerNoteImpl instance = null;

    private static Logger logger = LoggerFactory.getLogger(ControllerNoteImpl.class);

    private String packagePath;

    public static ControllerNoteImpl getInstance(String packagePath) {
        try {
            if(instance != null){//懒汉式

            }else{
                synchronized (ControllerNoteImpl.class) {
                    if(instance == null){//二次检查
                        instance = new ControllerNoteImpl(packagePath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private ControllerNoteImpl(String packagePath) {
        this.packagePath = packagePath;
    }

    public boolean initController(Map<String, Class> map) throws Exception {
        List<Class<?>> classes = NoteUtils.getClasses(packagePath);

        for (Class<?> clazz : classes) {
            //判断是否为Controller注解类
            if (clazz.isAnnotationPresent(Controller.class)) {
                //获取Annotation对象
                Controller controller = clazz.getAnnotation(Controller.class);

                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    //判断是否为RequestBody注解函数 请求数据就是key value 形式
                    if (method.isAnnotationPresent(RequestBody.class)) {
                        RequestBody requestBody = method.getAnnotation(RequestBody.class);
                        map.put(StringUtils.getUrlValue(controller.value() + requestBody.value()), RequestBodyServletProxy.class);
                        //把请求url 和 url指向Class和函数 加入如关系Mapper
                        Constants.controllerMapper.put(requestBody.value(), new ControllerModel(clazz, method));
                        continue;
                    }

                    //判断是否为RequestBodyJson注解函数 请求数据就是json形式
                    if (method.isAnnotationPresent(RequestBodyJson.class)) {
                        RequestBody requestBody = method.getAnnotation(RequestBody.class);
                        map.put(StringUtils.getUrlValue(controller.value() + requestBody.value()), RequestBodyJsonServletProxy.class);
                        //把请求url 和 url指向Class和函数 加入如关系Mapper
                        Constants.controllerMapper.put(requestBody.value(), new ControllerModel(clazz, method));
                        continue;
                    }
                }

                logger.infoRed(clazz.getName());
            }
        }



        return true;
    }


}
