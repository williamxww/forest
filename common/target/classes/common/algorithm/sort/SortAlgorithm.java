package common.algorithm.sort;

import java.util.Arrays;

/**
 *
 * @author vv
 * @since 2016/5/4.
 */
public class SortAlgorithm {

    /**
     * 冒泡排序
     *
     * @param ary
     */
    public int[] bubble(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            // 每次确保ary[i]是最小的
            for (int j = i + 1; j < ary.length; j++) {
                if (ary[i] > ary[j]) {
                    int temp = ary[j];
                    ary[j] = ary[i];
                    ary[i] = temp;
                }
            }
        }
        return ary;
    }

    /**
     * 将数据插入到已经排好的序列中
     *
     * @param ary
     */
    public static int[] insert(int[] ary) {
        for (int i = 1; i < ary.length; i++) {
            // key就是一个临界点（哨兵）,前面是已经排好序的，后面是未排序的
            int key = ary[i], j;
            // 用key和他前面的值依次比较，发现比该值大就让其后移一位
            for (j = i - 1; j >= 0 && ary[j] > key; j--) {
                ary[j + 1] = ary[j];
            }
            // 走到这里说明key>ary[j],因为之前j是已经-1啦，所以在此+1
            ary[j + 1] = key;
        }
        return ary;
    }

    public static void main(String[] args) {
        SortAlgorithm algorithm = new SortAlgorithm();
        System.out.println(Arrays.toString(algorithm.bubble(new int[] { 2, 5, 2, 1, 7, 3 })));
        System.out.println(Arrays.toString(algorithm.insert(new int[] { 2, 5, 2, 1, 7, 3 })));
    }

}
