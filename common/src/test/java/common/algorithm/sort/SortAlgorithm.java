package common.algorithm.sort;

import java.util.Arrays;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年5月4日
 * @since SDP V300R003C10
 */
public class SortAlgorithm {

    public void bubble(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            // 始终确保了ary[i]上面是最小的
            for (int j = i + 1; j < ary.length; j++) {
                if (ary[i] > ary[j]) {
                    int temp = ary[j];
                    ary[j] = ary[i];
                    ary[i] = temp;
                }
            }
        }
    }

    /**
     * 将数据插入到已经排好的序列中
     *
     * @param ary
     */
    public static void insert(int[] ary) {
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
        System.out.println(Arrays.toString(ary));
    }

    /**
     * 通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小
     * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     *
     * @param ary
     */
    public int[] quickSort(int[] ary) {
        quickSortRecusive(ary, 0, ary.length-1);
        return ary;
    }

    private void quickSortRecusive(int[] list, int low, int high) {
        if (low < high) {
            int middle = partition(list, low, high);  //将list数组进行一分为二
            quickSortRecusive(list, low, middle - 1);        //对低字表进行递归排序
            quickSortRecusive(list, middle + 1, high);       //对高字表进行递归排序
        }
    }

    /**
     * 以list的第一个为中轴pivot = list[low];比pivot小的放在左边，比其大的放在右边，最后返回pivot
     * @param list
     * @param low list的最低位
     * @param high list的最高位
     * @return
     */
    private int partition(int[] list, int low, int high) {
        //数组的第一个作为中轴,低位的值给了pivot，所以list[low]没有用了
        int pivot = list[low];
        while (low < high) {
            //高位比中轴大，则高位索引-1
            while (low < high && list[high] >= pivot) {
                high--;
            }
            //高位的值比中轴小，则放到list[low]上，此后list[high]又没用了
            list[low] = list[high];

            //低位比中轴小，则低位索引+1
            while (low < high && list[low] <= pivot) {
                low++;
            }
            list[high] = list[low];   //比中轴大的记录移到高端
        }
        //此处low==high
        list[low] = pivot;
        //返回中轴的位置
        return low;
    }

    public static void main(String[] args) {
        SortAlgorithm algorithm = new SortAlgorithm();
        System.out.println(Arrays.toString(algorithm.quickSort(new int[]{2,5,2,1,7,3})));
    }


}
