package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/1 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1775 {
    /**
     * @Description:
     * 给你两个长度可能不等的整数数组 nums1 和 nums2 。两个数组中的所有值都在 1 到 6 之间（包含 1 和 6）。
     *
     * 每次操作中，你可以选择 任意 数组中的任意一个整数，将它变成 1 到 6 之间 任意 的值（包含 1 和 6）。
     *
     * 请你返回使 nums1 中所有数的和与 nums2 中所有数的和相等的最少操作次数。如果无法使两个数组的和相等，请返回 -1 。
     *
     * tips:
     * 1 <= nums1.length, nums2.length <= 10^5
     * 1 <= nums1[i], nums2[i] <= 6
     * @param nums1 
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2025/4/1 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums1, int[] nums2) {
        int result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description:
     * 1. 判断不存在的情况, 即 -1, nums1的最大: 6n, 最小: n; nums2 最大: 6m, 最小 m,
     *  a. nums1 > nums2 => n > 6m;
     *  b. nums1 < nums2 => 6n < m;
     * 2. sum1 = sum(nums1[i]), sum2 = sum(nums2[j]);
     * 3. 判断sum1 与 sym2 的关系, 基本思路是 排序 + 贪心, 方法是, 让sum1 与 sum2的差值减少
     * 4. sum1 > sum2 => diff, max1 => 1 = diff1, min2 => 6 = diff2, if diff > diff1 >= diff2
     *
     * AC: 2ms/55.93MB
     * @param nums1 
     * @param nums2 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/1 11:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        if (6 * n < m || 6 * m < n) {
            return -1;
        }
        int[] cnt1 = new int[7];
        int[] cnt2 = new int[7];
        int diff = 0;
        for (int i : nums1) {
            ++cnt1[i];
            diff += i;
        }
        for (int i : nums2) {
            ++cnt2[i];
            diff -= i;
        }
        if (diff == 0) {
            return 0;
        }
        if (diff > 0) {
            return help(cnt2, cnt1, diff);
        }
        return help(cnt1, cnt2, -diff);
    }

    public int help(int[] h1, int[] h2, int diff) {
        int[] h = new int[7];
        for (int i = 1; i < 7; ++i) {
            h[6 - i] += h1[i];
            h[i - 1] += h2[i];
        }
        int res = 0;
        for (int i = 5; i > 0 && diff > 0; --i) {
            int t = Math.min((diff + i - 1) / i, h[i]);
            res += t;
            diff -= t * i;
        }
        return res;
    }

}
