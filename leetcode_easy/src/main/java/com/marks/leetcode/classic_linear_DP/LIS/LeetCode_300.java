package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称: 最长递增子序列(longest increasing subsequence) </p>
 * <p>文件名称: 最长递增子序列模板 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 14:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_300 {
    /**
     * @Description: [
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的
     * 子序列
     * tips:
     * 1 <= nums.length <= 2500
     * -10^4 <= nums[i] <= 10^4
     * 。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/14 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lengthOfLIS(int[] nums) {
        int result = 0;
//        result = method_01(nums);
        result = method_02(nums);
        return result;
    }
    /**
     * @Description: [贪心算法 + 二分查找
     * 看不太懂题解
     * 题解的思路是:
     * 贪心:为使得递增子序列的长度尽可能的长，需要让子序列递增的每次添加的数字尽可能的小
     * 他可能得到的不是正确的序列, 但是如果只是求长度, 这种足够了</p>
     * <img src= "https://writings.sh/assets/images/posts/longest-increasing-subsequence-revisited/lis-seq-4.gif" />
     * or <src ="resources/images/LeetCode_300_method_01.gif" />
     *
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/14 16:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int len = 1;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n + 1];
        dp[len] = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > dp[len]) {
                len++;
                dp[len] = nums[i];
            }else {
                // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                // 使用Arrays.binarySearch 二分查找库来简化代码(不要重复造轮子)
                // 但是使用Arrays.binarySearch() 会产生额外的空间, 使得空间复杂度提高
                int pos = 0;
                int targetIndex = Arrays.binarySearch(dp, 1, len + 1, nums[i]);
                if (targetIndex > 0) {
                    pos = targetIndex;
                }else {
                    pos = Math.abs(targetIndex) - 1;
                }
                dp[pos] = nums[i];
            }
        }
        return len;
    }

    /**
     * @Description: [
     * 动态规划
     * dp[i]=max(dp[j])+1,其中0≤j<i且num[j]<num[i]
     * E1:
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     * dp[0] = 1
     * 10 > 9
     * dp[1] = 1
     * ...
     * i = 5
     * dp[] = {1, 1, 1, 2, 2}
     * nums[i] = 7
     * 存在以下情况
     * j = 2
     * dp[i] > dp[j] dp[i] = Math.max(dp[i], dp[j] + 1)
     * j = 3
     * dp[i] > dp[j] dp[i] = Math.max(dp[i], dp[j] + 1)
     * j = 4
     * dp[i] > dp[j] dp[i] = Math.max(dp[i], dp[j] + 1)
     * dp[5] = 3
     * 时间复杂度:O(n^2)
     * 空间复杂度:O(n)
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/14 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            // 遍历nums[0 ~ (i - 1)], 查找其中nums[i] > nums[j]的值, 更新dp[i] = Math.max(dp[i], dp[j] + 1)
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
