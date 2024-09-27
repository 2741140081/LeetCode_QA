package com.marks.leetcode.partition_DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 18:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2547 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k 。
     *
     * 将数组拆分成一些非空子数组。拆分的 代价 是每个子数组中的 重要性 之和。
     *
     * 令 trimmed(subarray) 作为子数组的一个特征，其中所有仅出现一次的数字将会被移除。
     *
     * 例如，trimmed([3,1,2,4,3,4]) = [3,4,3,4] 。
     * 子数组的 重要性 定义为 k + trimmed(subarray).length 。
     *
     * 例如，如果一个子数组是 [1,2,3,3,3,4,4] ，trimmed([1,2,3,3,3,4,4]) = [3,3,3,4,4] 。这个子数组的重要性就是 k + 5 。
     * 找出并返回拆分 nums 的所有可行方案中的最小代价。
     *
     * 子数组 是数组的一个连续 非空 元素序列
     *
     * tips:
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] < nums.length
     * 1 <= k <= 10^9
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 18:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * E1:nums = [1,2,1,2,1,3,3], k = 2
     * result = 8
     * 动态规划
     * 对于nums[0], dp[0] = Math.min()
     * 0~i 已经最小代价划分 dp[i]
     * i+1~j, 是否需要划分
     * 1.划分 dp[j] = dp[i] + (k + len)
     * 2.不划分 dp[j] = dp[i - 1] + (newLen)
     *
     * AC:275ms/54.84MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/24 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[][] pre = new int[n][n];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (j > i) {
                    pre[i][j] = pre[i][j - 1];
                }
                Integer merge = map.merge(nums[j], 1, Integer::sum);
                if (merge == 2) {
                    pre[i][j] += 2;
                } else if (merge > 2) {
                    pre[i][j]++;
                }
            }
            map.clear();
        }
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = k;
        for (int i = 1; i <= n; i++) {
            dp[i] = pre[0][i - 1] + k;
            for (int j = i - 1; j >= 0; j--) {
                dp[i] = Math.min(dp[i], dp[j] + pre[j][i - 1] + k);
            }
        }
        return dp[n];
    }
}
