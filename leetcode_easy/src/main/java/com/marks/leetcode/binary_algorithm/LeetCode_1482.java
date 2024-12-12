package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/22 16:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1482 {
    /**
     * @Description: [
     * 给你一个整数数组 bloomDay，以及两个整数 m 和 k 。
     * 现需要制作 m 束花。制作花束时，需要使用花园中 相邻的 k 朵花 。
     * 花园中有 n 朵花，第 i 朵花会在 bloomDay[i] 时盛开，恰好 可以用于 一束 花中。
     * 请你返回从花园中摘 m 束花需要等待的最少的天数。如果不能摘到 m 束花则返回 -1 。
     *
     * tips:
     * bloomDay.length == n
     * 1 <= n <= 10^5
     * 1 <= bloomDay[i] <= 10^9
     * 1 <= m <= 10^6
     * 1 <= k <= n
     * ]
     * @param bloomDay 
     * @param m 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/22 16:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDays(int[] bloomDay, int m, int k) {
        int result;
        result = method_01(bloomDay, m, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
     * 输出：12
     * 解释：要制作 2 束花，每束需要 3 朵。
     * 花园在 7 天后和 12 天后的情况如下：
     * 7 天后：[x, x, x, x, _, x, x]
     * 可以用前 3 朵盛开的花制作第一束花。但不能使用后 3 朵盛开的花，因为它们不相邻。
     * 12 天后：[x, x, x, x, x, x, x]
     * 显然，我们可以用不同的方式制作两束花。
     *
     * AC:28ms/58.20MB
     * ]
     * @param bloomDay 
     * @param m 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/22 16:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if ((long)m * k > n) {
            return -1;
        }
        // 二分法, 边界left, right
        int left = Arrays.stream(bloomDay).min().getAsInt();
        int right = Arrays.stream(bloomDay).max().getAsInt();
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (checkConditionIsMeet(bloomDay, mid, m, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean checkConditionIsMeet(int[] bloomDay, int mid, int m, int k) {
        int n = bloomDay.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (bloomDay[i] <= mid) {
                count++;
            } else {
                count = 0;
            }

            if (count == k) {
                m--;
                count = 0;

                if (m == 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
