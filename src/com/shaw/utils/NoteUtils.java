package com.shaw.utils;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;
import com.shaw.note.AutoBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class NoteUtils {

    private static Logger logger = LoggerFactory.getLogger(NoteUtils.class);

    public static void initNote(Class<?> clazz, HttpServletRequest request) throws Exception {
        // Request传过来的所有内容
        Map<String, String[]> map = new HashMap<>();

        map = request.getParameterMap();

        // 所有全部变量和属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 判断是否为注释的对象
            if (field.isAnnotationPresent(AutoBody.class)) {

                // 获取注解修饰对象的Class
                Field[] tempFields = field.getClass().getDeclaredFields();

                for (String key : map.keySet()) {
                    String [] strs = (String[]) map.get(key);

                    for (Field tempField : tempFields) {
                        if (tempField.getGenericType().toString().equals("class java.lang.String") && ("set" + StringUtils.getMethodName(tempField.getName())).equals(("set" + StringUtils.getMethodName(key)))) {
                            logger.info("属性名>>>>>" + tempField.getName());
                            Method method = (Method) tempField.getClass().getMethod("set" + StringUtils.getMethodName(field.getName()),String.class);
                            method.invoke(field,tempField.getName() != null ? strs[0] : null);//给属性赋值
                        }
                        if (tempField.getGenericType().toString().equals("class java.lang.Integer") && ("set" + StringUtils.getMethodName(tempField.getName())).equals(("set" + StringUtils.getMethodName(key)))) {
                            logger.info("属性名>>>>>" + tempField.getName());
                            Method method = (Method) tempField.getClass().getMethod("set" + StringUtils.getMethodName(field.getName()),Integer.class);
                            method.invoke(field,tempField.getName() != null ? Integer.parseInt(strs[0]) : null);//给属性赋值
                        }
                    }

                }

                // 获取Annotation对象
                //AutoBody autoBody = field.getAnnotation(AutoBody.class);
                //logger.info(autoBody.cls());


            }
        }

    }

    public static void initNote(Object obj_cls,String id, String name) throws Exception {
        // 所有全部变量和属性
        Field[] fields = obj_cls.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 判断是否为注释的对象
            if (field.isAnnotationPresent(AutoBody.class)) {
                // 可以给私有变量赋值
                field.setAccessible(true);
                // 获取注解修饰对象的Class
                logger.info("fieldClass:" + field.getClass().toString());
                Field[] tempFields = field.getType().getDeclaredFields();
                // 获取函数class
                String classPath = field.getGenericType().getTypeName();
                // 通过class创建对象
                Object object = Class.forName(classPath).newInstance();
                for (Field tempField : tempFields) {
                    logger.info("Name:" + tempField.getName() + "----------Type:" + tempField.getGenericType().getTypeName());
                    if (tempField.getGenericType().getTypeName().equals("java.lang.String") && ("set" + StringUtils.getMethodName(tempField.getName())).equals(("set" + StringUtils.getMethodName(name)))) {
                        Method method = (Method) Class.forName(classPath).getMethod("set" + StringUtils.getMethodName(tempField.getName()),String.class);
                        method.invoke(object,tempField.getName() != null ? "给Name赋值!" : null);//给属性赋值
                    }
                    if (tempField.getGenericType().getTypeName().equals("java.lang.Integer") && ("set" + StringUtils.getMethodName(tempField.getName())).equals(("set" + StringUtils.getMethodName(id)))) {
                        Method method = (Method) Class.forName(classPath).getMethod("set" + StringUtils.getMethodName(tempField.getName()),Integer.class);
                        method.invoke(object,tempField.getName() != null ? 100 : null);//给属性赋值
                    }
                }

                field.set(obj_cls, object);

            }
        }

    }

    /**
     * 从包package中获取所有的Class
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(String packageName){

        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)){
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive){
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
