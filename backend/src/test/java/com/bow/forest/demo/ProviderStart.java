package com.bow.forest.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vv on 2016/7/30.
 */
public class ProviderStart {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "META-INF/spring/dubbo-service-provider.xml"
        });
        context.start();

        System.in.read();//test git
    }
}
