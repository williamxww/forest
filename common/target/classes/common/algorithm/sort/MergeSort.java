package common.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序是分治法（Divide and Conquer）的一个非常典型的应用<br/>
 * 将已有序的子序列合并，得到完全有序的序列<br/>
 * 两个有序序列段分别为 R[low, mid] 和 R[mid+1, high]<br/>
 * 每次从两个段中取出第一个(最小的)比较，将较小者放入temp中。最后将各段中余下的部分直接复制到temp中。
 *
 * <pre>
 *     {5, 4, 3, 2, 2, 4, 1}
 *     {4,5} {2,3}  {2,2} {4,1}
 *     {2,3,4,5} {1,2,2,4}
 *     {1,2,2,2,3,4,4,5}
 * </pre>
 * 
 * @author vv
 * @since 2016/9/20.
 */
public class MergeSort {

    /**
     * 对数组的begin到end 进行排序
     *
     * @param array 需要排序的数组
     * @param begin 起始位置
     * @param end 终止位置
     */
    public void sort(int[] array, int begin, int end) {
        // 分到最后，i==j说明该组中只有一个元素了
        if (begin < end) {
            int middle = (begin + end) / 2;
            // 递归处理相关的合并事项
            sort(array, begin, middle);// 对第一组排序
            sort(array, middle + 1, end);// 对第二组排序
            merge(array, begin, middle, end);// 合并两组
        }
    }

    /**
     * 对这两组进行合并，从每组中选择最小值进行对比，小的放到temp中
     *
     * @param array 需要排序的原始数组
     * @param low low--middle 为第一组
     * @param middle 分界点
     * @param high middle+1--high 为第二组
     */
    private void merge(int[] array, int low, int middle, int high) {
        int i = low; // i是第一段序列的下标
        int j = middle + 1; // j是第二段序列的下标
        int k = 0; // k是临时存放合并序列的下标
        int[] temp = new int[high - low + 1]; // array2是临时合并序列

        while (i <= middle && j <= high) {
            // 判断第一段和第二段取出的数哪个更小，将其存入合并序列temp，并继续向下扫描
            if (array[i] <= array[j]) {
                temp[k] = array[i];
                i++;
            } else {
                temp[k] = array[j];
                j++;
            }
            k++;
        }

        // 若第一段序列还没扫描完，将其全部复制到合并序列
        while (i <= middle) {
            temp[k] = array[i];
            i++;
            k++;
        }

        // 若第二段序列还没扫描完，将其全部复制到合并序列
        while (j <= high) {
            temp[k] = array[j];
            j++;
            k++;
        }

        // 将合并序列复制到原始序列中
        for (k = 0, i = low; i <= high; i++, k++) {
            array[i] = temp[k];
        }
    }

    public static void main(String[] args) {
        int[] ary = { 5, 4, 3, 2, 2, 4, 1 };
        MergeSort sort = new MergeSort();
        sort.sort(ary, 0, ary.length - 1);
        System.out.println(Arrays.toString(ary));
    }
}
