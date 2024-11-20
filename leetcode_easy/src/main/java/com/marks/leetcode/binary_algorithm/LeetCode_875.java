package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/20 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_875 {
    /**
     * @Description: [
     * 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
     *
     * 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。
     * 如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
     *
     * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     * 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
     * 
     * tips:
     * 1 <= piles.length <= 10^4
     * piles.length <= h <= 10^9
     * 1 <= piles[i] <= 10^9
     * ]
     * @param piles
     * @param h
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minEatingSpeed(int[] piles, int h) {
        int result;
        result = method_01(piles, h);
        return result;
    }
    
    /**
     * @Description: [
     * 输入：piles = [3,6,7,11], h = 8
     * 输出：4
     * left = 1, right = 11, mid = 6, total = 1 + 1 + 2 + 2 = 6 < 8
     * left = 1, right = 6, mid = 3, total = 1 + 2 + 3 + 4 = 10 > 8
     * left = 4, right = 6, mid = 5, total = 1 + 2 + 2 + 3 = 8 = 8
     * left = 4, right = 5, mid = 4, total = 1 + 2 + 2 + 3 = 8 = 8
     * left = 4, right = 4, break;
     *
     * AC:13ms/44.12MB
     * ]
     * @param piles 
     * @param h 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 16:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] piles, int h) {
        int left = 1;
        int right = Arrays.stream(piles).max().getAsInt(); // piles[]的最大值, 超过这个值没有意义
        while (left < right) {
            int mid = (right - left) / 2 + left;
            int total = getTotalHours(piles, mid);
            if (total <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int getTotalHours(int[] piles, int mid) {
        int total = 0;
        for (int pile : piles) {
            /*
            查看官方题解后
            方式1
            int curTime = pile / mid + (pile % mid == 0 ? 0 : 1);
            total += curTime;
            更加好的写法是
            方式2
            int curTime = (pile + mid - 1) / mid;
            total += curTime;

            方式1:AC:11ms
            方式2:AC:8ms

            方式2的优点:
            1.减少了条件判断‌:方式2避免了使用条件表达式（三元运算符）
            2.‌数学上的优雅‌：通过简单的数学变换实现了向上取整
            3.‌性能优化‌：虽然在这种简单的整数运算中性能差异可能微乎其微，但在大量重复运算中，避免条件分支可以带来微小的性能提升

            解释方式2:
            1.如果pile % mid == 0, 即pile 可以整除 mid, 那么 (pile + (mid - 1)) / mid = pile / mid
            2.如果pile % mid != 0, 不能整除, 那么(pile + (mid - 1)) / mid = pile / mid + 1
            相当于 (pile % mid + (mid - 1)) / mid = 1, 只要存在余数, 那么必定会多加一个1

             */
//            total += (pile / mid + (pile % mid == 0 ? 0 : 1));
            int curTime = (pile + mid - 1) / mid;
            total += curTime;
        }
        return total;
    }
}
