package com.marks.leetcode.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/5 15:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3082 {
    public final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [
     * 给你一个长度为 n 的整数数组 nums 和一个 正 整数 k 。
     * 一个整数数组的 能量 定义为和 等于 k 的子序列的数目。
     * 请你返回 nums 中所有子序列的 能量和 。
     * 由于答案可能很大，请你将它对 10^9 + 7 取余 后返回。
     * tips:
     * 1 <= n <= 100
     * 1 <= nums[i] <= 10^4
     * 1 <= k <= 100
     * ]
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/8/5 16:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int sumOfPower(int[] nums, int k) {
        int result = 0;
//        result = method_01(nums, k); // 暴力破解行不通, 超时
        result = method_02(nums, k);
        return result;
    }

    private int method_02(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum < k) {
            return 0;
        }
        int len = nums.length;
        int[][][] dp = new int[2][k + 1][len + 1];
        dp[0][0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= len; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = nums[i - 1];
            for (int j = 0; j <= k; j++) {
                for (int p = 0; p <= i; p++) {
                    dp[curr][j][p] = (j >= temp && p >= 1) ? (dp[pre][j][p] + dp[pre][j - temp][p - 1]) % MOD :
                            (dp[pre][j][p]) % MOD;
                }
            }
        }
        long result = 0;
        for (int p = len, power = 1; p > 0; p--, power = power * 2 % MOD) {
            result = (result + (long) dp[curr][k][p] * power) % MOD;
        }
        return (int) result;
    }

    private List<List<Integer>> allSubList = new ArrayList<>();

    private int res;
    private int method_01(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum < k) {
            return 0;
        }
        getAllSubArray(nums, 0, new ArrayList<>());

        for (List<Integer> list : allSubList) {
            int[] subNums = list.stream().mapToInt(Integer::intValue).toArray();
            int subNumsSum = Arrays.stream(subNums).sum();
            if (subNumsSum < k) {
                continue;
            }else if(subNumsSum == k){
                res = (res + 1) % MOD;
            }else {
                // subNumsSum > k
                res = (res + getCountBySubArray(subNums, k)) % MOD;
            }
        }
        return res;
    }

    private int getCountBySubArray(int[] subNums, int k) {
        int len = subNums.length;
        int[][] dp = new int[2][k + 1];
        int curr = 0;
        dp[0][0] = 1;
        for (int i = 1; i <= len; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = subNums[i - 1];
            for (int j = 0; j <= k; j++) {
                dp[curr][j] = dp[pre][j] % MOD;
                if (j >= temp) {
                    dp[curr][j] = (dp[curr][j] + dp[pre][j - temp]) % MOD;
                }
            }
        }
        return dp[curr][k];
    }

    private void getAllSubArray(int[] nums, int index, List<Integer> subList) {
        if (index == nums.length) {
            if (subList.size() > 0) {
                allSubList.add(new ArrayList<>(subList));
            }
            return;
        }
        // 不选择当前元素
        getAllSubArray(nums, index + 1, subList);

        // 选择当前元素
        subList.add(nums[index]);
        getAllSubArray(nums, index + 1, subList);
        subList.remove(subList.size() - 1); // 回溯 移除选择的元素
    }
}
