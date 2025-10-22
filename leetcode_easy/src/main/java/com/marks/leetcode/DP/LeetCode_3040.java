package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/17 11:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3040 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，如果 nums 至少 包含 2 个元素，你可以执行以下操作中的 任意 一个：
     *
     * 选择 nums 中最前面两个元素并且删除它们。
     * 选择 nums 中最后两个元素并且删除它们。
     * 选择 nums 中第一个和最后一个元素并且删除它们。
     * 一次操作的 分数 是被删除元素的和。
     *
     * 在确保 所有操作分数相同 的前提下，请你求出 最多 能进行多少次操作。
     *
     * 请你返回按照上述要求 最多 可以进行的操作次数。
     *
     * tips:
     * 2 <= nums.length <= 2000
     * 1 <= nums[i] <= 1000
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/10/17 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxOperations(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private int[][] memo;
    private int[] nums;

    /**
     * @Description:
     * 1. 尝试动态规划
     * 2. 尝试记忆化搜索
     *
     * AC: 150ms/75.39MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/10/17 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        memo = new int[n][n];
        this.nums = nums;
        int ans = 0;
        ans = Math.max(ans, dp(0, n - 1, nums[0] + nums[n - 1]));
        ans = Math.max(ans, dp(0, n - 1, nums[0] + nums[1]));
        ans = Math.max(ans, dp(0, n - 1, nums[n - 2] + nums[n - 1]));
        return ans;
    }

    private int dp(int i, int j, int target) {
        for (int k = 0; k < nums.length; k++) {
            Arrays.fill(memo[k], -1);
        }
        return dfs(i, j, target);
    }

    private int dfs(int i, int j, int target) {
        if (i >= j) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int ans = 0;
        if (nums[i] + nums[i + 1] == target) {
            ans = Math.max(ans, dfs(i + 2, j, target) + 1);
        }
        if (nums[j] + nums[j - 1] == target) {
            ans = Math.max(ans, dfs(i, j - 2, target) + 1);
        }
        if (nums[i] + nums[j] == target) {
            ans = Math.max(ans, dfs(i + 1, j - 1, target) + 1);
        }
        memo[i][j] = ans;
        return ans;
    }


    private int ans = 0;
    
    /**
     * @Description:
     * E1:
     * 输入：nums = [3,2,1,2,3,4]
     * 输出：3
     * 1. 感觉回溯法会好写一点, 试试写写看
     * 2. int left = 0, right = nums.length - 1; int score = 0; int count = 0; 记录删除的分数和次数
     * 3. score = nums[left] + nums[right]; score = nums[left] + nums[left + 1]; score = nums[right - 1] + nums[right];
     * 3 种情况,
     *
     * 超时!!! 修改成记忆化搜索
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/17 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int score = 0, count = 0;

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                score = nums[left] + nums[left + 1];
                backTracking(nums, left + 2, right, score, count + 1);
            } else if (i == 1) {
                score = nums[left] + nums[right];
                backTracking(nums, left + 1, right - 1, score, count + 1);
            } else {
                score = nums[right] + nums[right - 1];
                backTracking(nums, left, right - 2, score, count + 1);
            }
        }
        return ans;
    }

    private void backTracking(int[] nums, int left, int right, int score, int count) {
        ans = Math.max(ans, count);
        if (left >= right) {
            return;
        }

        if (nums[left] + nums[right] == score) {
            backTracking(nums, left + 1, right - 1, score, count + 1);
        }

        if (nums[left] + nums[left + 1] == score) {
            backTracking(nums, left + 2, right, score, count + 1);
        }

        if (nums[right - 1] + nums[right] == score) {
            backTracking(nums, left, right - 2, score, count + 1);
        }
    }

}
