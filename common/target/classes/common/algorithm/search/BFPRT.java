package common.algorithm.search;

import common.algorithm.sort.MergeSort;

/**
 * BFPRT算法解决的问题十分经典，即从某n个元素的序列中选出第i小的元素
 * 时间复杂度O(N)
 * 算法步骤：
 * 1. 将n个元素每5个一组，分成n/5(上界)组。
 * 2. 取出每一组的中位数，任意排序方法，比如插入排序。
 * 3. 递归的调用selection算法查找上一步中所有中位数的中位数，设为x，偶数个中位数的情况下设定为选取中间小的一个。
 * 4. 用x来分割数组，设小于等于x的个数为k，大于x的个数即为n-k。
 * 5. 若i==k，返回x；若i<k，在小于x的元素中递归查找第i小的元素；
 * 若i>k，在大于x的元素中递归查找第i-k小的元素。终止条件：n=1时，返回的即是i小元素。
 *
 * @author vv
 * @since 2016/9/20.
 */
public class BFPRT {
    //求数组a下标l到r中的第id个数
    private int search(int ary[], int left, int right, int id) {
        //小于等于5个数，直接排序得到结果
        if (right - left + 1 <= 5) {
            insertionSort(ary, left, right);
            return ary[left + id - 1];
        }

        int t = left - 1; //当前替换到前面的中位数的下标
        for (int start = left, end; (end = start + 4) <= right; start += 5) //每5个进行处理
        {
            insertionSort(ary, start, end); //5个数的排序
            t++;
            //将中位数替换到数组前面，便于递归求取中位数的中位数
            swap(ary[t], ary[start + 2]);
        }

        int pivotId = (left + t) >> 1; //l到t的中位数的下标，作为主元的下标
        search(ary, left, t, pivotId - left + 1);//不关心中位数的值，保证中位数在正确的位置
        int m = partition(ary, left, right, pivotId), cur = m - left + 1;
        if (id == cur) {
            return ary[m];                   //刚好是第id个数
        } else if (id < cur) {
            return search(ary, left, m - 1, id);//第id个数在左边
        } else {
            return search(ary, m + 1, right, id - cur);         //第id个数在右边
        }
    }

    private void insertionSort(int[] ary, int low, int high) {

    }

    int partition(int a[], int l, int r, int pivotId) //对数组a下标从l到r的元素进行划分
    {
        //以pivotId所在元素为划分主元
        swap(a[pivotId], a[r]);

        int j = l - 1; //左边数字最右的下标
        for (int i = l; i < r; i++)
            if (a[i] <= a[r])
                swap(a[++j], a[i]);
        swap(a[++j], a[r]);
        return j;
    }

    private void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    public static void main(String[] args) {
        System.out.println(9 >> 1);
    }
}
