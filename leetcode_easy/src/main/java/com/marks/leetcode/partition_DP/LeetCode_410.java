package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/27 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_410 {
    /**
     * @Description: [
     * 给定一个非负整数数组 nums 和一个整数 k ，你需要将这个数组分成 k 个非空的连续子数组。
     *
     * 设计一个算法使得这 k 个子数组各自和的最大值最小。
     *
     * tips:
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 10^6
     * 1 <= k <= min(50, nums.length)
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/27 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int splitArray(int[] nums, int k) {
        int result = 0;
//        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description: [
     * 官方题解:方式二 <二分法 + 贪心>
     * 贪心地模拟分割的过程，从前到后遍历数组，用 sum 表示当前分割子数组的和，cnt 表示已经分割出的子数组的数量（包括当前子数组），
     * 那么每当 sum 加上当前值超过了 x，我们就把当前取的值作为新的一段分割子数组的开头，并将 cnt 加 1。遍历结束后验证是否 cnt 不超过 m。
     * E1: nums = [7,2,5,10,8], k = 2
     * 1. left = 10, right = 32, mid = 21, count = 2, check = true
     * 2. left = 10, right = 21, mid = 15, count = 3, check = false
     * 3. left = 16, right = 21, mid = 18, count = 2, check = true
     * 4. left = 16, right = 18, mid = 17, count = 3, check = false
     * 5. left = 18, right = 18, end
     * return 18;
     * 优雅实在优雅！！！
     * AC:2ms/40.21MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/27 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int left = Arrays.stream(nums).max().getAsInt();
        int right = Arrays.stream(nums).sum();
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (check(nums, mid, k)) {
                right = mid;
            }else {
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean check(int[] nums, int mid, int k) {
        int sum = 0;
        int count = 1;
        for (int i = 0; i < nums.length; i++) {
            if (sum + nums[i] > mid) {
                count++;
                sum = nums[i];
            }else {
                sum += nums[i];
            }
        }
        return count <= k;
    }

    /**
     * @Description: [
     * 官方题解: 方式一<动态规划>
     * AC:92ms/40.62MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/27 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        if (k == n) {
            return Arrays.stream(nums).max().getAsInt();
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        int[] sub = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sub[i + 1] = sub[i] + nums[i];
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                for (int l = 0; l < i; l++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[l][j - 1], sub[i] - sub[l]));
                }

            }
        }
        return dp[n][k];
    }
}
