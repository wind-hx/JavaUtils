package com.shaw.listener;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;
import com.shaw.note.impl.ControllerNoteImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class DemoListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(DemoListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ContextInitializedInit");
        Map<String, Class> map = new HashMap<>();
        ServletContext servletContext = servletContextEvent.getServletContext();

        //获取要扫描的包
        String scanPackage = servletContextEvent.getServletContext().getInitParameter("DemoListenerValue");
        //准备扫描所有Conroller
        ControllerNoteImpl controllerNoteImpl = ControllerNoteImpl.getInstance(scanPackage);
        try {
            controllerNoteImpl.initController(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String key : map.keySet()) {

            ServletRegistration servletRegistration = servletContext.addServlet(key, map.get(key));
            servletRegistration.addMapping(key);
            logger.infoRed("URL:" + key + "----->CLASS:" + map.get(key));
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ContextDestroyed");
    }
}
