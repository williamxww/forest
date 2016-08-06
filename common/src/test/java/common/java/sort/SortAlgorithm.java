package common.java.sort;

import java.util.Arrays;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年5月4日
 * @since SDP V300R003C10
 */
public class SortAlgorithm
{
    
    public void bubble(int[] ary)
    {
        for (int i = 0; i < ary.length; i++)
        {
            // 始终确保了ary[i]上面是最小的
            for (int j = i + 1; j < ary.length; j++)
            {
                if (ary[i] > ary[j])
                {
                    int temp = ary[j];
                    ary[j] = ary[i];
                    ary[i] = temp;
                }
            }
        }
    }
    
    public static void insert(int[] ary)
    {
        for (int i = 1; i < ary.length; i++)
        {
            // key就是一个临界点（哨兵）,前面是已经排好序的，后面是未排序的
            int key = ary[i], j;
            // 用key和他前面的值依次比较，发现比该值大就让其后移一位
            for (j = i - 1; j >= 0 && ary[j] > key; j--)
            {
                ary[j + 1] = ary[j];
            }
            // 走到这里说明key>ary[j],因为之前j是已经-1啦，所以在此+1
            ary[j + 1] = key;
        }
        System.out.println(Arrays.toString(ary));
    }
    
    public static void main(String[] args)
    {
        insert(new int[] {5, 2, 6, 1, 0, 3});
    }

}
