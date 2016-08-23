package com.bow.forest.demo;

import com.huawei.openas.dsf.DSFStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vv on 2016/8/13.
 */
public class DsfTest {

    private static final Logger runLog = LoggerFactory.getLogger(DsfTest.class);

    private static ClassPathXmlApplicationContext context = null;

    private static boolean inited = false;

    public static void addShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    DSFStartup.destroy();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }));
    }

    public static void initDsf()
    {
        if (!inited)
        {
            runLog.info("DsfClientUtil start....");
            try
            {
                runLog.info("dsf bean out Start:");
                context = new ClassPathXmlApplicationContext();
                context.setConfigLocations(new String[] { "classpath*:/META-INF/spring/*.dsf.import.service.xml",
                        "classpath*:/META-INF/spring/*-dsf-import-service.xml",
                        "classpath*:/META-INF/spring/*.dsf.service.xml" });
                context.refresh();
                runLog.info("dsf bean out End:");

                addShutdownHook();

                runLog.info("Init DSF Start...");
                DSFStartup.init();

                runLog.info("Init DSF End...");
            }
            catch (Throwable e)
            {
                e.printStackTrace();
                runLog.error("initDsf error", e);
                throw new RuntimeException(e);
            }

            runLog.info("SubscribeStarter init finished!");
            inited = true;
        }
    }

    public static void main(String[] args) {
        initDsf();
    }

}
