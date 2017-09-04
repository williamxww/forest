package common.algorithm.structure;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * PriorityQueue保存队列元素的顺序并不是按加入队列的顺序，而是构建了一棵最小堆树a[i] > a[2i+1] 和 a[2i+2] <br/>
 * poll方法取出队列中最小的元素。
 * 
 * @author vv
 * @since 2017/9/4.
 */
public class PriorityQueueDemo {

    @Test
    public void demo() {
        PriorityQueue pq = new PriorityQueue();
        pq.offer(6);
        pq.offer(-3);
        pq.offer(20);
        pq.offer(18);
        pq.offer(9);
        pq.offer(30);
        pq.offer(7);
        // 注意此处构建了一个最小堆 a[i] > a[2i+1] 和 a[2i+2]
        System.out.println(pq); // [-3, 6, 7, 18, 9, 30, 20]
        // 每次取出的一定是最小的
        System.out.println(pq.poll()); // -3
        // 取出最小值后堆会做相应调整
        System.out.println(pq); // [6, 9, 7, 18, 20, 30]
        System.out.println(pq.poll()); // 6
        System.out.println(pq.poll()); // 7
        System.out.println(pq.poll()); // 9
    }
}
