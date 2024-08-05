package com.marks.leetcode.knapsack;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/2 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2787 {

    public final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [给你两个 正 整数 n 和 x 。
     * 请你返回将 n 表示成一些 互不相同 正整数的 x 次幂之和的方案数。
     * 换句话说，你需要返回互不相同整数 [n1, n2, ..., nk] 的集合数目，满足 n = n1^x + n2^x + ... + nk^x 。
     * 由于答案可能非常大，请你将它对 10^9 + 7 取余后返回。
     * 比方说，n = 160 且 x = 3 ，一个表示 n 的方法是 n = 2^3 + 3^3 + 5^3 。]
     * @param n 
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfWays(int n, int x) {
        int result = 0;
        result = method_01(n, x);
        return result;
    }

    /**
     * @Description: [
     * 在1-n 的n个整数集合, 对集合中的所有元素进行x次方Math.pow(2, 3) 即 2^3 = 8
     * E1:
     * 输入：n = 10, x = 2
     * 输出：1
     * 解释：我们可以将 n 表示为：n = 3^2 + 1^2 = 10 。
     * 这是唯一将 10 表达成不同整数 2 次方之和的方案。
     * 1 - 10 这10个数的集合中找到方案
     * if Math.pow(i, x) <= n
     * ArrayList.add(Math.pow(i, x));
     * 将Array转为int数组 nums
     * 现在目标明确: 在nums[] 中找到和为 n 的方案数, 并且需要对方案数 % MOD
     * 滚动数组
     * dp[2][n+1]
     *
     * ]
     * @param n
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int x) {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int tempValue = (int) Math.pow(i, x);
            if (tempValue <= n) {
                list.add(tempValue);
            }
        }
        int[] nums = list.stream().mapToInt(Integer::intValue).toArray();
        int len = nums.length;
        int[][] dp = new int[2][n+1];
        dp[0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= len; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = nums[i-1];
            for (int j = 0; j <= n; j++) {
                dp[curr][j] = dp[pre][j] % MOD;
                if (temp <= j) {
                    dp[curr][j] = (dp[curr][j] + dp[pre][j - temp]) % MOD;
                }
            }
        }

        return dp[curr][n];
    }
}
