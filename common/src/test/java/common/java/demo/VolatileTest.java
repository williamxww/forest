package common.java.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by vv on 2016/8/13.
 */
public class VolatileTest {
    private static volatile int a = 0;
    private static void inc(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a++;
    }
    public static void main(String[] args) {
        int threadNum = 10000;
        final CountDownLatch latch = new CountDownLatch(threadNum);
        for(int a=0;a<threadNum;a++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    VolatileTest.inc();
                    latch.countDown();
                }
            });
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("FINAL "+a);
    }
}
