package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1027 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1027 {


    /**
     * @Description:
     * 给你一个整数数组 nums，返回 nums 中最长等差子序列的长度。
     *
     * 回想一下，nums 的子序列是一个列表 nums[i1], nums[i2], ..., nums[ik] ，且 0 <= i1 < i2 < ... < ik <= nums.length - 1。
     * 并且如果 seq[i+1] - seq[i]( 0 <= i < seq.length - 1) 的值都相同，那么序列 seq 是等差的。
     * tips:
     * 2 <= nums.length <= 1000
     * 0 <= nums[i] <= 500
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestArithSeqLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. nums[] 是一个无序的数组, 不可用Set 或者其他来进行查看元素是否存在
     * 2. 应该使用int[][] dp = new int[nums.length][1001]; dp[i][sub] = len; sub 表示等差值, 范围是 0 - 500,
     * 应该还可以是负数, int sub = nums[j] - nums[i], sub 可能小于0, 重新定义sub 范围 -500 ~ 500 之间 => 将范围
     * 转换为index 下标 [0 ~ 1000], dp[n][1001]
     * 3. 转移方程 当前节点为 i, 值为 nums[i]; 前一个节点j 在[0 ~ i - 1] 遍历 int index = nums[i] - nums[j] + 500;(转换为下标)
     * dp[i][index] = dp[j][index] + 1; 先给dp[][] 初始化一个值, dp[i][j] = 1;
     * AC: 54ms/65.89MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][1001];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], 1);
        }
        int ans = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int index = nums[i] - nums[j] + 500;
                dp[i][index] = Math.max(dp[i][index], dp[j][index] + 1);
                ans = Math.max(ans, dp[i][index]);
            }
        }
        return ans;
    }

}
