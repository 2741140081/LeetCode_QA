package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/18 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1955 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 特殊序列 是由 正整数 个 0 ，紧接着 正整数 个 1 ，最后 正整数 个 2 组成的序列。
     *
     * 比方说，[0,1,2] 和 [0,0,1,1,1,2] 是特殊序列。
     * 相反，[2,1,0] ，[1] 和 [0,1,2,0] 就不是特殊序列。
     * 给你一个数组 nums （仅 包含整数 0，1 和 2），请你返回 不同特殊子序列的数目 。由于答案可能很大，请你将它对 10^9 + 7 取余 后返回。
     *
     * 一个数组的 子序列 是从原数组中删除零个或者若干个元素后，剩下元素不改变顺序得到的序列。如果两个子序列的 下标集合 不同，那么这两个子序列是 不同的 。
     * tips:
     * 1 <= nums.length <= 105
     * 0 <= nums[i] <= 2
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/18 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSpecialSubsequences(int[] nums) {
        int result = 0;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }
    /**
     * @Description: [基于method_01, 使用空间优化
     * AC:16ms/55.95MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/18 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int[] dp = new int[3];
        dp[0] = nums[0] == 0 ? 1 : 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] == 0) {
                dp[0] = (2 * dp[0] % MOD) + 1;
                dp[1] = dp[1];
                dp[2] = dp[2];
            } else if (nums[i] == 1) {
                dp[1] = (2 * dp[1] % MOD  + dp[0]) % MOD;
                dp[0] = dp[0];
                dp[2] = dp[2];
            } else if (nums[i] == 2) {
                dp[2] = (2 * dp[2] % MOD + dp[1]) % MOD;
                dp[0] = dp[0];
                dp[1] = dp[1];

            }
        }
        return dp[2];
    }

    /**
     * @Description: [
     * 案例E1:nums = [0,1,2,0,1,2]
     * dp[0] = 1
     * dp[1] = 1
     * dp[2] = 1
     * dp[0] = 3
     * dp[1] = dp[0] + dp[1] = 4 (0 1 1, 0 0 1, 0 1, 0 1)
     * dp[2] = dp[1] + dp[2] = 4 + 1 = 5
     *
     * 记得之前好像写过一个类似的案例
     * dp[2] 是由dp[1], dp[2] 转移得到,
     * dp[1] 是由dp[0], dp[1] 转移得到
     * dp[0] 是由dp[0], 0 转移得到
     * if nums[i] == 0
     * dp[i][0] = 2 * dp[i - 1][0] + 1
     * dp[i][1] = dp[i - 1][1]
     * dp[i][2] = dp[i - 1][2]
     *
     * nums[i] == 1
     * dp[i][0] = dp[i - 1][0]
     * dp[i][1] = 2 * dp[i - 1][1] + dp[i - 1][0]
     * dp[i][2] = dp[i - 1][2]
     *
     * nums[i] == 2
     * dp[i][0] = dp[i - 1][0]
     * dp[i][1] = dp[i - 1][2]
     * dp[i][2] = 2 * dp[i - 1][2] + dp[i - 1][1]
     * AC:72ms/58.38MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/18 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][3];
        dp[0][0] = nums[0] == 0 ? 1 : 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] == 0) {
                dp[i][0] = (2 * dp[i - 1][0] % MOD) + 1;
                dp[i][1] = dp[i - 1][1];
                dp[i][2] = dp[i - 1][2];
            } else if (nums[i] == 1) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = (2 * dp[i - 1][1] % MOD  + dp[i - 1][0]) % MOD;
                dp[i][2] = dp[i - 1][2];
            } else if (nums[i] == 2) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1];
                dp[i][2] = (2 * dp[i - 1][2] % MOD + dp[i - 1][1]) % MOD;
            }
        }
        return dp[n - 1][2];
    }
}
