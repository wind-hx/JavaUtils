package com.shaw.log;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public class LoggerFactory {

    public static Logger getLogger(Class obj) {
        return  new Logger(obj.getName());
    }


}
