package com.marks.leetcode.knapsack;

import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2915 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int result = 0;
//        result = method_01(nums, target);
//        result = method_02(nums, target);
        result = method_03(nums, target);
        return result;
    }

    /**
     * @Description: [
     * 不太能直观的看出dp的状态转移方程
     * 先看案例
     * E1:
     * nums = [1,2,3,4,5], target = 9
     * [4, 5] = 9
     * [1, 3, 5]
     * [2, 3, 5]
     * 定义dp[i][j]
     * i表示最大子序列的长度, j表示前0 ~ i的和
     * 初始化
     * dp[0][j] = -1 (不存在)
     * dp[0][0] = 0;
     * dp[1][1] = 1
     * if j < nums[i]
     * dp[i][j] = dp[i][j-1]
     *
     * nums = [1,2,3,4,5], target = 9
     * dp[i][j]
     * i= 0 ~ 5
     * 0    -1  -1  -1  -1
     *
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/7/29 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * 1 <= target <= 1000
     */
    private int method_01(List<Integer> nums, int target) {
        int n = nums.size();
        int[] ints = nums.stream().mapToInt(Integer::intValue).toArray();

        int[][] dp = new int[n + 1][target + 1];
        Arrays.fill(dp[0], Integer.MIN_VALUE);
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                int temp = ints[i - 1];
                if (temp > j) {
                    dp[i][j] = dp[i-1][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-temp] + 1);
                }

            }
        }
        int ans = dp[n][target];

        return ans <= 0 ? -1 : ans;
    }

    private int method_02(List<Integer> numList, int target) {
        int[] nums = numList.stream().mapToInt(i -> i).toArray();
        int len = nums.length;
        int[][] f = new int[len+1][target+1];
        Arrays.fill(f[0], Integer.MIN_VALUE);
        f[0][0] = 0;
        for(int i=0;i < len;i++){
            int x = nums[i];
            for(int c = 0; c <= target; c++){
                if(c < x) f[i+1][c] = f[i][c];
                else f[i+1][c] = Math.max(f[i][c], f[i][c - x] + 1);
            }
        }
        int ans = f[len][target];
        return ans <= 0 ? -1 : ans;
    }
    /**
     * @Description: [
     * 使用滑动数组优化二维dp数组空间
     * 由于dp[i][j] 只与 dp[i-1][j] 或者 dp[i-1][j-temp] 有关
     * 所以使用二维数组进行优化
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/7/31 16:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(List<Integer> nums, int target) {
        int n = nums.size();
        int[] ints = nums.stream().mapToInt(Integer::intValue).toArray();

        int[][] dp = new int[2][target + 1];
        Arrays.fill(dp[0], Integer.MIN_VALUE);
        dp[0][0] = 0;

        int curr = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                int temp = ints[i - 1];
                curr = i % 2;
                int pre = 1 - curr;
                if (temp > j) {
                    dp[curr][j] = dp[pre][j];
                }else {
                    dp[curr][j] = Math.max(dp[pre][j], dp[pre][j-temp] + 1);
                }

            }
        }
        int ans = dp[curr][target];

        return ans <= 0 ? -1 : ans;
    }



}
