package com.yml;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-1117:15
 */


public class MySpringApplication {
    public static void run(Class clazz){
        // 创建web服务器：tomcat 内嵌
        createTomcat();
    }

    private static void createTomcat(){
        //TODO 创建Tomcat

    }
}
