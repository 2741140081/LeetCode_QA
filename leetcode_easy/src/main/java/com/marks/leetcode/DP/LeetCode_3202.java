package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 17:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3202 {

    
    /**
     * @Description:
     * 给你一个整数数组 nums 和一个 正 整数 k 。
     * nums 的一个 子序列 sub 的长度为 x ，如果其满足以下条件，则称其为 有效子序列 ：
     *
     * (sub[0] + sub[1]) % k == (sub[1] + sub[2]) % k == ... == (sub[x - 2] + sub[x - 1]) % k
     * 返回 nums 的 最长有效子序列 的长度。
     *
     * tips:
     * 2 <= nums.length <= 10^3
     * 1 <= nums[i] <= 10^7
     * 1 <= k <= 10^3
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 17:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumLength(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    
    /**
     * @Description:
     * dp[i][prev][k] = dp[i-1][nums[i]][k] + 1
     * E1:
     * 1,2,3,10,2  k = 6
     * => 1,2,3,4,2
     * dp[0][1] = 1, i = 1, dp[1][3] = 2, dp[1][1] = 1, dp[1][2] = 1
     * i = 2; dp[2][4] = 2, dp[2][5] = 2, dp[2][3] = Math.max(2, 1), dp[]
     * dp[0][1][1] = 1, i = 1; dp[1][2][3] = 2, dp[1][2][2] = 1; dp[1][1][1] = 1;
     * i = 2; dp[i][j][h] = Math.max(dp[1][j][(j + nums[i]) % k], dp[i][j][])
     * dp[1][2][2 + 3] =
     * 真正的状态转移方程
     * dp[prev][curr] = dp[curr][prev] + 1
     * AC: 44ms/55.36MB
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 17:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[][] dp = new int[k][k];

        for (int i = 0; i < n; i++) {
            nums[i] %= k;
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int curr = nums[i];
            for (int j = 0; j < k; j++) {
                // k * x + mod = x + y, y = nums[i], mod = [0 ~ k - 1]
                dp[curr][j] = dp[j][curr] + 1;
                ans = Math.max(ans, dp[curr][j]);
            }
        }
        
        return ans;
    }

}
