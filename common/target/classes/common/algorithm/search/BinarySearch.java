package common.algorithm.search;

/**
 * 二分查找又称折半查找 查找有序数组中的元素
 * O(log n)
 *
 * @author vv
 * @since 2016/9/20.
 */
public class BinarySearch {

    public int search(int[] ary, int target, int low, int high) {
        int mid = (low + high) / 2;
        if (target > ary[mid]) {
            return search(ary, target, mid + 1, high);
        } else if (target < ary[mid]) {
            return search(ary, target, low, mid - 1);
        } else if (target == ary[mid]) {
            return mid;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        int[] ary = {1, 3, 6, 7, 8, 9};
        BinarySearch search = new BinarySearch();
        System.out.println(search.search(ary, 6, 0, ary.length - 1));
    }
}
