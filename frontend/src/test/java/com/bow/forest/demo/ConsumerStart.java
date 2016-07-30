package com.bow.forest.demo;

import com.bow.forest.common.service.IDemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vv on 2016/7/30.
 */
public class ConsumerStart {


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "META-INF/spring/dubbo-service-consumer.xml"
        });
        IDemoService service = context.getBean("demoService", IDemoService.class);
        System.out.println(">>>>>>>>"+service.getName());
    }
}
