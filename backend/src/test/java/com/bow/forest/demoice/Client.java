package com.bow.forest.demoice;

import com.bow.forest.backend.serviceice.HelloWorldPrx;
import com.bow.forest.backend.serviceice.HelloWorldPrxHelper;

/**
 * @author vv
 * @since 2017/1/24.
 */

public class Client {

    public static void main(String[] args) {
        int status = 0;
        // Communicator实例
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);

            // 根据servant的名称以及服务器ip、端口获取远程服务代理
            Ice.ObjectPrx base = ic.stringToProxy("helloWorld:default -p 10000");

            // 获取远端接口的代理
            HelloWorldPrx helloWorld = HelloWorldPrxHelper.checkedCast(base);
            if (helloWorld == null) {
                throw new RuntimeException("Invalid proxy");
            }

            helloWorld.say("vv");
        } catch (Ice.LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        System.exit(status);
    }
}
