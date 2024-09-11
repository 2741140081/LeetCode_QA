package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/9 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2771 {
    /**
     * @Description: [
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，长度均为 n 。
     *
     * 让我们定义另一个下标从 0 开始、长度为 n 的整数数组，nums3 。对于范围 [0, n - 1] 的每个下标 i ，你可以将 nums1[i] 或 nums2[i] 的值赋给 nums3[i] 。
     *
     * 你的任务是使用最优策略为 nums3 赋值，以最大化 nums3 中 最长非递减子数组 的长度。
     *
     * 以整数形式表示并返回 nums3 中 最长非递减 子数组的长度。
     *
     * 注意：子数组 是数组中的一个连续非空元素序列。
     *
     * tips:
     * 1 <= nums1.length == nums2.length == n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^9
     * ]
     * @param nums1 
     * @param nums2 
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxNonDecreasingLength(int[] nums1, int[] nums2) {
        int result = 0;
//        result = method_01(nums1, nums2);
//        result = method_02(nums1, nums2);
        result = method_03(nums1, nums2);
        return result;
    }

    /**
     * @Description: [查看题解后写出
     * AC:6ms/57.63MB
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/9/10 16:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        Arrays.fill(dp1, 1);
        Arrays.fill(dp2, 1);
        int ans = 1;
        for (int i = 1; i < n; i++) {
            if (nums1[i] >= nums1[i - 1]) {
                dp1[i] = Math.max(dp1[i], dp1[i - 1] + 1);
            }
            if (nums1[i] >= nums2[i - 1]) {
                dp1[i] = Math.max(dp1[i], dp2[i - 1] + 1);
            }
            if (nums2[i] >= nums2[i - 1]) {
                dp2[i] = Math.max(dp2[i], dp2[i - 1] + 1);
            }
            if (nums2[i] >= nums1[i - 1]) {
                dp2[i] = Math.max(dp2[i], dp1[i - 1] + 1);
            }
            ans = Math.max(ans, Math.max(dp1[i], dp2[i]));
        }
        return ans;
    }

    /**
     * @Description: [
     * 遇事不决想想动态规划
     * int[][] dp = new int[n][8]
     * 1.对于nums3[i], 他能取得值必然来自nums1[i] 或者 nums2[i]
     * 存在多少种可能性
     * 0.nums1[i] > nums1[i - 1] && nums1[i] > nums2[i - 1]
     * 1.nums1[i] > nums1[i - 1] && nums1[i] < nums2[i - 1]
     * 2.nums1[i] < nums1[i - 1] && nums1[i] > nums2[i - 1]
     * 3.nums1[i] < nums1[i - 1] && nums1[i] < nums2[i - 1]
     *
     * 4.nums2[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]
     * 5.nums2[i] > nums2[i - 1] && nums2[i] < nums1[i - 1]
     * 6.nums2[i] < nums2[i - 1] && nums2[i] > nums1[i - 1]
     * 7.nums2[i] < nums2[i - 1] && nums2[i] < nums1[i - 1]
     *
     * dp[i][0] = Math.max(dp[i - 1][0] + 1, dp[i - 1][1] + 1)
     * dp[i][1] = Math.max(dp[i - 1][0] + 1, dp[i - 1][1] + 1)
     *
     * // 仍然存在问题
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @Deprecated
    private int method_02(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] dp = new int[n][2];
        Arrays.fill(dp[0], 1);
        int ans = 0;
        for (int i = 1; i < n; i++) {
            if (nums1[i] >= nums1[i - 1] || nums1[i] >= nums2[i - 1]) {
                if (nums1[i] >= nums1[i - 1] && nums1[i] >= nums2[i - 1]) {
                    dp[i][0] = Math.max(dp[i - 1][0] + 1, dp[i - 1][1] + 1);
                } else if (nums1[i] >= nums1[i - 1] && nums1[i] < nums2[i - 1]) {
                    dp[i][0] = dp[i - 1][0] + 1;
                } else if (nums1[i] < nums1[i - 1] && nums1[i] >= nums2[i - 1]) {
                    dp[i][0] = dp[i - 1][1] + 1;
                }
            }else {
                dp[i][0] = 1;
            }

            if (nums2[i] >= nums2[i - 1] || nums2[i] >= nums1[i - 1]) {
                if (nums2[i] >= nums2[i - 1] && nums2[i] >= nums1[i - 1]) {
                    dp[i][1] = Math.max(dp[i - 1][0] + 1, dp[i - 1][1] + 1);
                } else if (nums2[i] >= nums2[i - 1] && nums2[i] < nums1[i - 1]) {
                    dp[i][1] = dp[i - 1][0] + 1;
                } else if (nums2[i] < nums2[i - 1] && nums2[i] >= nums1[i - 1]) {
                    dp[i][1] = dp[i - 1][1] + 1;
                }
            }else {
                dp[i][1] = 1;
            }
            ans = Math.max(ans, Math.max(dp[i][0], dp[i][1]));
        }
        return ans;
    }

    /**
     * @Description: [
     * 存在问题, 就是可能当前我放置了一个比较大的数, 但是我可能需要的是一个更小的数, 以便使得数组非递减
     *
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/9/9 17:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @Deprecated
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] nums3 = new int[n];
        int pre = 0;
        nums3[0] = Math.min(nums1[0], nums2[0]);
        for (int i = 1; i < n; i++) {
            pre = nums3[i - 1];
            if (pre > Math.max(nums1[i], nums2[i]) || pre < Math.min(nums1[i], nums2[i])) {
                nums3[i] = Math.min(nums1[i], nums2[i]);
            }else {
                if (pre > nums1[i] && pre < nums2[i]) {
                    nums3[i] = nums2[i];
                }else {
                    nums3[i] = nums1[i];
                }
            }
        }
        int ans = 1;
        int temp = 1;
        for (int i = 1; i < n; i++) {
            if (nums3[i] >= nums3[i - 1]) {
                temp++;
            }else {
                temp = 1;
            }
            ans = Math.max(ans, temp);
        }
        return ans;
    }
}
