package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/25 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2226 {
    /**
     * @Description: [
     * 给你一个 下标从 0 开始 的整数数组 candies 。数组中的每个元素表示大小为 candies[i] 的一堆糖果。
     * 你可以将每堆糖果分成任意数量的 子堆 ，但 无法 再将两堆合并到一起。
     *
     * 另给你一个整数 k 。你需要将这些糖果分配给 k 个小孩，使每个小孩分到 相同 数量的糖果。
     * 每个小孩可以拿走 至多一堆 糖果，有些糖果可能会不被分配。
     * 返回每个小孩可以拿走的 最大糖果数目 。
     *
     * tips:
     * 1 <= candies.length <= 10^5
     * 1 <= candies[i] <= 10^7
     * 1 <= k <= 10^12
     * ]
     * @param candies 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumCandies(int[] candies, long k) {
        int result;
        result = method_01(candies, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：candies = [5,8,6], k = 3
     * 输出：5
     * left = 0, right = 8, mid = 4, check(4) = true
     * left = 5, right = 8, mid = 6, check(6) = false
     * left = 4, right = 5, mid = 4, check(4) = true;
     * left = 5, right = 5, mid = 5, check(5) = true;
     * left = 6, right = 5, break
     *
     * AC:28ms/61.40MB
     * ]
     * @param candies
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] candies, long k) {
        int left = 1;
        int right = Arrays.stream(candies).max().getAsInt(); // candies[max] 最大值
        int ans = 0;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (checkMaxCandy(candies, mid, k)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    private boolean checkMaxCandy(int[] candies, int mid, long k) {
        for (int candy : candies) {
            if (mid != 0) {
                int count = candy / mid;
                k -= count;
            }

            if (k <= 0) {
                return true;
            }
        }
        return false;
    }
}
