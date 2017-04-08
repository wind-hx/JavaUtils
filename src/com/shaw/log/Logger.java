package com.shaw.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public class Logger {

    private String className;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setClassName(String className) {
        this.className = className;
    }

    public Logger(String className) {
        this.className = className;
    }

    public void info(String message) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        System.out.println(simpleDateFormat.format(new Date()) + " [ FileName:" + ste.getFileName() + " - ClassName: " + className + " ] " + ste.getLineNumber() + " - " + message);
    }

    public void infoRed(String message) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        System.err.println(simpleDateFormat.format(new Date()) + " [ FileName:" + ste.getFileName() + " - ClassName: " + className + " ] " + ste.getLineNumber() + " - " + message);
    }

    public void info(Object obj) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        System.out.println(simpleDateFormat.format(new Date()) + " [ FileName:" + ste.getFileName() + " - ClassName: " + className + " ] " + ste.getLineNumber() + " - " + obj.toString());
    }

}
