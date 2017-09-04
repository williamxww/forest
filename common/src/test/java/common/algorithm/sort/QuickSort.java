package common.algorithm.sort;

import java.util.Arrays;

/**
 * 该方法的基本思想是：<br/>
 * 1.先从数列中取出一个数作为基准数。<br/>
 * 2.分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。<br/>
 * 3.再对左右区间重复第二步，直到各区间只有一个数。<br/>
 *
 * 复杂度O(N*logN)
 * 
 * @author vv
 * @since 2017/9/4.
 */
public class QuickSort {

    /**
     * 通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小
     * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     *
     * @param ary
     */
    public int[] sort(int[] ary) {
        quickSort(ary, 0, ary.length - 1);
        return ary;
    }

    private void quickSort(int[] ary, int low, int high) {
        if (low < high) {
            // 将ary数组进行一分为二
            int middle = partition(ary, low, high);
            // 对低字表进行递归排序
            quickSort(ary, low, middle - 1);
            // 对高字表进行递归排序
            quickSort(ary, middle + 1, high);
        }
    }

    /**
     * 以list的第一个为中轴pivot = ary[low];比pivot小的放在左边，比其大的放在右边，最后返回pivot
     * 
     * @param ary
     * @param low ary的最低位
     * @param high ary的最高位
     * @return
     */
    private int partition(int[] ary, int low, int high) {
        // 数组的第一个作为中轴,低位的值给了pivot，所以ary[low]空出来了
        int pivot = ary[low];
        while (low < high) {
            // 高位比中轴大，则高位索引-1
            while (low < high && ary[high] >= pivot) {
                high--;
            }
            // 高位的值比中轴小，则放到ary[low]上，此后ary[high]又空出来了
            ary[low] = ary[high];

            // 低位比中轴小，则低位索引+1
            while (low < high && ary[low] <= pivot) {
                low++;
            }
            // 比中轴大的记录移到高端
            ary[high] = ary[low];
        }
        // 此处low==high
        ary[low] = pivot;
        // 返回中轴的位置
        return low;
    }

    public static void main(String[] args) {
        QuickSort sorter = new QuickSort();
        System.out.println(Arrays.toString(sorter.sort(new int[] { 2, 5, 2, 1, 7, 3 })));
    }
}
