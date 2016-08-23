package common.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * <P>所有线程都会被barrier.await();<br/>
 * 只有当到达barrier.await()的数量等于创建时给定的值，唤醒所有线程
 * Created by vv on 2016/8/23.
 */
public class CyclicBarrierDemo {
    private static int num = 3;

    public void withRunnable(){

        //注意此栅栏带有runnable,即放开栅栏后首先执行此runnable然后各个线程执行各自的代码
        final CyclicBarrier barrier = new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("begin");
            }
        });

        for (int i = 0; i <num ; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"ready");
                    try {
                        barrier.await();
                        System.out.println(Thread.currentThread().getName()+"start");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            },"T"+i);
            t.start();

            //wait 1 second before start the next thread;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        demo.withRunnable();
        System.out.println("end");
    }
}
