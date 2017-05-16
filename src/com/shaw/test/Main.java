package com.shaw.test;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;
import com.shaw.model.TokenModel;
import com.shaw.note.AutoBody;
import com.shaw.utils.NoteUtils;
import com.shaw.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        Test test = new Test();

//        DelayQueue<TokenModel> delayQueue = new DelayQueue();
//        System.out.println(System.currentTimeMillis());
//
//        delayQueue.offer(new TokenModel("10s", 10));
//        delayQueue.offer(new TokenModel("12s", 12));
//        delayQueue.offer(new TokenModel("3s", 3));
//        delayQueue.offer(new TokenModel("8s", 8));
//
//        while (true) {
//            System.out.println(delayQueue.take());
//        }

        //KeyStore keyStore = loadKetStore("E:\\Tomcat\\tomcat_https1\\apache-tomcat-8.5.4\\745764370@qq.com_sha256_cn.pfx", "745764370hexu");
        //System.out.println("Alias:" + getKeyAlias(keyStore));


        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd");
        String str1 = simpleDateFormat1.format(new Date());
        System.out.println(str1);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("M");
        String str2 = simpleDateFormat2.format(new Date());
        System.out.println(Integer.toHexString(Integer.parseInt(str2)).toUpperCase());

        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("y");
        String str3 = simpleDateFormat3.format(new Date());
        str3 = str3.substring(str3.length() -1);
        System.out.println(str3);



    }


}
