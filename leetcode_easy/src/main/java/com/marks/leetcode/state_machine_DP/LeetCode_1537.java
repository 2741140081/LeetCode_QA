package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/18 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1537 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 你有两个 有序 且数组内元素互不相同的数组 nums1 和 nums2 。
     *
     * 一条 合法路径 定义如下：
     *
     * 选择数组 nums1 或者 nums2 开始遍历（从下标 0 处开始）。
     * 从左到右遍历当前数组。
     * 如果你遇到了 nums1 和 nums2 中都存在的值，那么你可以切换路径到另一个数组对应数字处继续遍历（但在合法路径中重复数字只会被统计一次）。
     * 得分 定义为合法路径中不同数字的和。
     *
     * 请你返回 所有可能 合法路径 中的最大得分。由于答案可能很大，请你将它对 10^9 + 7 取余后返回。
     *
     * tips:
     * 1 <= nums1.length, nums2.length <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^7
     * nums1 和 nums2 都是严格递增的数组。
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/9/18 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSum(int[] nums1, int[] nums2) {
        int result = 0;
        result = method_01(nums1, nums2);
        return result;
    }
    /**
     * @Description: [
     * nums1 = [2,4,5,8,10],
     * nums2 = [4,6,8,9]
     *
     * AC:4ms/55.63MB
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/9/18 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int firstIndex = 0;
        int secondIndex = 0;
        long firstSum = 0;
        long secondSum = 0;

        while (firstIndex < n || secondIndex < m) {
            if (firstIndex < n && secondIndex < m) {
                if (nums1[firstIndex] > nums2[secondIndex]) {
                    secondSum += nums2[secondIndex];
                    secondIndex++;
                }else if (nums1[firstIndex] < nums2[secondIndex]) {
                    firstSum += nums1[firstIndex];
                    firstIndex++;
                }else if (nums1[firstIndex] == nums2[secondIndex]) {
                    long best = Math.max(firstSum, secondSum) + nums1[firstIndex];
                    firstSum = best;
                    secondSum = best;
                    firstIndex++;
                    secondIndex++;
                }
            } else if (firstIndex < n) {
                firstSum += nums1[firstIndex];
                firstIndex++;
            } else if (secondIndex < m) {
                secondSum += nums2[secondIndex];
                secondIndex++;
            }
        }
        return (int) (Math.max(firstSum, secondSum) % MOD);
    }
}
