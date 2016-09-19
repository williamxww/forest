package common.algorithm.sort;


import java.util.Arrays;

/**
 * 构建一个最大堆后，0号元素为最大值，将其换到队尾，重新调整最大堆再找最大值
 *
 * 堆是一种重要的数据结构，为一棵完全二叉树, 底层如果用数组存储数据的话，假设某个元素为序号为i(Java数组从0开始,i为0到n-1),
 * 如果它有左子树，那么左子树的位置是2i+1，如果有右子树，右子树的位置是2i+2，如果有父节点，父节点的位置是(i-1)/2取整。
 * 分为最大堆和最小堆，最大堆的任意子树根节点不小于任意子结点，最小堆的根节点不大于任意子结点。
 * 所谓堆排序就是利用堆这种数据结构来对数组排序，我们使用的是最大堆。处理的思想和冒泡排序，选择排序非常的类似，一层层封顶，
 * 只是最大元素的选取使用了最大堆。最大堆的最大元素一定在第0位置，构建好堆之后，交换0位置元素与顶即可。
 * 堆排序为原位排序(空间小), 且最坏运行时间是O(nlgn)，是渐进最优的比较排序算法。
 * @author vv
 * @since 2016/9/19.
 */
public class HeapSort {

    public void swap(int[] data, int i, int j) {
        if (i == j) {
            return;
        }
        //data[i] data[j] 交换
        data[i] = data[i] + data[j];
        data[j] = data[i] - data[j];
        data[i] = data[i] - data[j];
    }



    /**
     * 此方法可以确保index对应的子树中，所有的根节点一定是最大的
     *
     * parent left right 找一个最大的放到parent所在位置，然后重排子树
     * @param array
     * @param heapSize
     * @param parent 父节点的序号
     */
    private void maxHeap(int[] array, int heapSize, int parent) {
        int left = parent * 2 + 1;
        int right = parent * 2 + 2;

        int largest = parent;
        if (left < heapSize && array[left] > array[parent]) {
            largest = left;
        }

        if (right < heapSize && array[right] > array[largest]) {
            largest = right;
        }

        if (parent != largest) {
            swap(array, parent, largest);
            //前一步交换后，可能需要重排子树
            maxHeap(array, heapSize, largest);
        }
    }

    private void buildMaxHeap(int[] array) {

        //最后一个节点为序号array.length-1，从其父节点开始
        int maxParentIndex = (array.length-2) / 2;
        for (int i = maxParentIndex; i >= 0; i--) {
            maxHeap(array, array.length, i);
        }
    }

    public void sort(int[] array) {
        buildMaxHeap(array);
        for (int i = array.length - 1; i >= 1; i--) {
            //将最大值换到最后
            swap(array, 0, i);
            //更换过后需要调整堆排序，注意此时heapSize已经-1了
            maxHeap(array, i, 0);
        }
    }

    public static void main(String[] args) {
        int[] array = { 9, 8, 3, 6, 5, 4, 7, 2, 1, 0, -1, -2, -3 };
        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);
        System.out.println(Arrays.toString(array));
    }

}
