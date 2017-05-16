package com.shaw.utils;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public class StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 判断非空
     * @param objects 字符串数组
     * @return true or false
     * */
    public static boolean myIsEmpty(Object... objects) {
        for (Object temp : objects) {
            if (temp == null || temp.equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 把字符串第一个字符转大写
     * @param str 字符串
     * @return 一个新的字符串
     * */
    public static String getMethodName(String str) throws Exception {
        char[] cs = str.toCharArray();
        if(cs[0] >='a' && cs[0] <='z' ) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }


    /**
     * 提取request里的键值对 通过反射来把键值对转换成 对应的pojo
     * @param request request对象
     * @param objClass 需要转的对象class
     * @param parameterName 注解里的请求参数名称 直接调用getParameter取值就可以
     * @return 你需要的对象
     * */
    public static <T> T requestConvertPOJO(HttpServletRequest request, Class<T> objClass, String parameterName) throws Throwable {

        if (objClass == null) {
            return null;
        }

        if (objClass.isPrimitive()) {
            if (objClass == byte.class) {
                return null;
            }

            if (objClass == short.class) {
                return null;
            }

            if (objClass == int.class) {
                return (T) (Integer)Integer.parseInt(request.getParameter(parameterName));
            }

            if (objClass == long.class) {
                return null;
            }

            if (objClass == float.class) {
                return null;
            }

            if (objClass == double.class) {
                return null;
            }

            if (objClass == char.class) {
                return null;
            }

            if (objClass == boolean.class) {
                return null;
            }
        }
        //判断函数参数类型是否是String
        if (objClass == String.class)
            return (T) request.getParameter(parameterName);

        //判断函数参数类型是否是Integer
        if (objClass == Integer.class)
            return (T) (Integer)Integer.parseInt(request.getParameter(parameterName));

        // 获取类的所有属性
        Field[] fields = objClass.getDeclaredFields();

        // Request传过来的所有内容
        Map<String, String[]> map = new HashMap<>();

        map = request.getParameterMap();

        Object tempobj = objClass.newInstance();

        for (String key : map.keySet()) {
            String [] strs = (String[]) map.get(key);
            for (Field field : fields) {
                if (field.getGenericType().toString().equals("class java.lang.String") && ("set" + getMethodName(field.getName())).equals(("set" + getMethodName(key)))) {
                    Method method = (Method) objClass.getMethod("set" + getMethodName(field.getName()),String.class);
                    method.invoke(tempobj,field.getName() != null ? strs[0] : null);//给属性赋值
                }
                if (field.getGenericType().toString().equals("class java.lang.Integer") && ("set" + getMethodName(field.getName())).equals(("set" + getMethodName(key)))) {
                    Method method = (Method) objClass.getMethod("set" + getMethodName(field.getName()),Integer.class);
                    method.invoke(tempobj,field.getName() != null ? Integer.parseInt(strs[0]) : null);//给属性赋值
                }
            }

        }
        return (T) tempobj;
    }

    /**
     * 判断字符串第一个是否/   是则返回 不是则加上
     * @param str 字符串
     * @return 一个新的字符串
     * */
    public static String getUrlValue(String str) throws Exception {
        char[] cs = str.toCharArray();
        if (cs[0] == '/')
            return String.valueOf(cs);
        return "/" + str;
    }


}
