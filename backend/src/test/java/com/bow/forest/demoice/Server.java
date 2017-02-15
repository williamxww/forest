package com.bow.forest.demoice;

/**
 * @author vv
 * @since 2017/1/24.
 */
public class Server {

    public static void main(String[] args) {
        int status = 0;
        // Communicator实例
        Ice.Communicator ic = null;
        try {
            // args可以传一些初使化参数，如连接超时时间，初使化客户连接池的数量等
            ic = Ice.Util.initialize(args);

            // 创建一个对象适配器，传入适配器名字和在10000端口处接收来的请求
            Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("HelloWorldAdapter", "default -p 10000");

            // 实例化一个PrinterI对象，为Printer接口创建一个servant
            Ice.Object object = new HelloWorldImpl();

            // 这里的“SimplePrinter”是Servant的名字
            adapter.add(object, Ice.Util.stringToIdentity("helloWorld"));

            // 被激活后，服务器开始处理来自客户的请求。
            adapter.activate();

            // 这个方法挂起发出调用的线程
            ic.waitForShutdown();
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
