package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3336 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3336 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 请你统计所有满足以下条件的 非空 子序列 对 (seq1, seq2) 的数量：
     * 子序列 seq1 和 seq2 不相交，意味着 nums 中 不存在 同时出现在两个序列中的下标。
     * seq1 元素的 GCD 等于 seq2 元素的 GCD。
     * 返回满足条件的子序列对的总数。
     *
     * 由于答案可能非常大，请返回其对 10^9 + 7 取余 的结果。
     * tips:
     * 1 <= nums.length <= 200
     * 1 <= nums[i] <= 200
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/14 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int subsequencePairCount(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: 统计满足条件的非空子序列对数量
     * 
     * 核心思路：
     * 1. 问题转化：找到两个不相交的子序列，它们的GCD相等
     * 2. 使用DP状态 dp[j][k] 表示处理到当前元素时，第一个子序列的GCD为j，第二个子序列的GCD为k的方案数
     * 3. 对于每个数字，有三种选择：
     *    - 不选入任何子序列（保持状态不变）
     *    - 选入第一个子序列（更新第一个子序列的GCD）
     *    - 选入第二个子序列（更新第二个子序列的GCD）
     * 4. 最终答案：所有dp[g][g]的和，其中g从1到m（两个子序列GCD相等的情况）
     * 
     * 状态转移：
     * - ndp[j][k] += dp[j][k] （当前数字不选）
     * - ndp[gcd(j, num)][k] += dp[j][k] （当前数字加入第一个子序列）
     * - ndp[j][gcd(k, num)] += dp[j][k] （当前数字加入第二个子序列）
     *
     * AC: 197ms/46.66MB
     * @param: nums 输入的整数数组
     * @return int 满足条件的子序列对数量（对MOD取余）
     * @author marks
     * @CreateDate: 2026/07/14 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        // 定义模数，防止结果溢出
        int MOD = 1000000007;
        
        // 找到数组中的最大值，作为DP数组的维度上限
        // 因为GCD的值不会超过数组中的最大值
        int m = 0;
        for (int num : nums) {
            m = Math.max(m, num);
        }

        // DP状态定义：dp[j][k] 表示第一个子序列GCD为j，第二个子序列GCD为k的方案数
        // 初始状态：dp[0][0] = 1 表示还没有选择任何元素时的基础状态
        int[][] dp = new int[m + 1][m + 1];
        dp[0][0] = 1;

        // 遍历数组中的每个数字，逐个考虑是否将其加入子序列
        for (int num : nums) {
            // 创建新的DP状态数组，用于存储加入当前数字后的状态
            int[][] ndp = new int[m + 1][m + 1];
            
            // 遍历所有可能的GCD状态组合
            for (int j = 0; j <= m; j++) {
                // 计算将当前数字加入第一个子序列后的新GCD值
                int divisor1 = gcd(j, num);
                
                for (int k = 0; k <= m; k++) {
                    // 获取当前状态的方案数
                    int val = dp[j][k];
                    
                    // 如果当前状态没有方案，跳过以提高效率
                    if (val == 0) {
                        continue;
                    }
                    
                    // 计算将当前数字加入第二个子序列后的新GCD值
                    int divisor2 = gcd(k, num);
                    
                    // 三种选择的累加：
                    // 1. 当前数字不选入任何子序列，保持原状态
                    ndp[j][k] = (ndp[j][k] + val) % MOD;
                    
                    // 2. 当前数字加入第一个子序列，更新第一个子序列的GCD
                    ndp[divisor1][k] = (ndp[divisor1][k] + val) % MOD;
                    
                    // 3. 当前数字加入第二个子序列，更新第二个子序列的GCD
                    ndp[j][divisor2] = (ndp[j][divisor2] + val) % MOD;
                }
            }
            
            // 更新DP状态，进入下一个数字的处理
            dp = ndp;
        }

        // 统计最终答案：所有两个子序列GCD相等的方案数之和
        // 只统计dp[j][j]，即两个子序列GCD都为j的情况
        int ans = 0;
        for (int j = 1; j <= m; j++) {
            ans = (ans + dp[j][j]) % MOD;
        }
        
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
