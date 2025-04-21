package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 14:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2971 {
    /**
     * @Description:
     * 给你一个长度为 n 的 正 整数数组 nums 。
     * 多边形 指的是一个至少有 3 条边的封闭二维图形。多边形的 最长边 一定 小于 所有其他边长度之和。
     *
     * 如果你有 k （k >= 3）个 正 数 a1，a2，a3, ...，ak 满足 a1 <= a2 <= a3 <= ... <= ak 且 a1 + a2 + a3 + ... + ak-1 > ak ，
     * 那么 一定 存在一个 k 条边的多边形，每条边的长度分别为 a1 ，a2 ，a3 ， ...，ak 。
     *
     * 一个多边形的 周长 指的是它所有边之和。
     * 请你返回从 nums 中可以构造的 多边形 的 最大周长 。如果不能构造出任何多边形，请你返回 -1 。
     *
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2025/3/28 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long largestPerimeter(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 排序 + 前缀和
     * pre[0] = 0;
     * pre[1] = pre[0] + nums[0]
     * pre[2] = pre[1] + nums[1] = nums[0] + nums[1]
     *
     * AC: 33ms/62.46MB
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2025/3/28 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        long[] pre = new long[n + 1];

        Arrays.sort(nums);

        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }

        long ans = -1;
        for (int i = n - 1; i >= 2; i--) {
            if (pre[i] > nums[i]) {
                ans = pre[i] + nums[i];
                break;
            }
        }
        return ans;
    }
}
