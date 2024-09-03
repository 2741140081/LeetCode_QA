package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/2 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2222 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的二进制字符串 s ，它表示一条街沿途的建筑类型，其中：
     * s[i] = '0' 表示第 i 栋建筑是一栋办公楼，
     * s[i] = '1' 表示第 i 栋建筑是一间餐厅。
     * 作为市政厅的官员，你需要随机 选择 3 栋建筑。然而，为了确保多样性，选出来的 3 栋建筑 相邻 的两栋不能是同一类型。
     *
     * 比方说，给你 s = "001101" ，我们不能选择第 1 ，3 和 5 栋建筑，因为得到的子序列是 "011" ，有相邻两栋建筑是同一类型，所以 不合 题意。
     * 请你返回可以选择 3 栋建筑的 有效方案数 。
     * tips:
     * 3 <= s.length <= 10^5
     * s[i] 要么是 '0' ，要么是 '1' 。
     * ]
     * @param s
     * @return long
     * @author marks
     * @CreateDate: 2024/9/2 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long numberOfWays(String s) {
        long result = 0;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }
    /**
     * @Description: [
     * 怎么优化?
     * 既然数量级是10^5
     * 那么大概率时间复杂度就是O(N)
     * E1:s = "001101"
     * 先找010这个
     * 假设i中间位置是1
     * 那么count_010 = sum_0(0 ~ i - 1) * sum_0(i + 1, n - 1)
     * AC:50ms/48.16MB
     * 优化zero[n] 后
     * AC:39ms/44.36MB
     * ]
     * @param s
     * @return long
     * @author marks
     * @CreateDate: 2024/9/3 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(String s) {
        int n = s.length();
        long[] dp = new long[n]; // 存储s.charAt(i) == '1'的个数
        dp[0] = s.charAt(0) == '1' ? 1 : 0;
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1];
            if (s.charAt(i) == '1') {
                dp[i]++;
            }
        }
        long sum_0 = n - dp[n - 1];
        long sum_1 = dp[n - 1];
        long res = 0;
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) == '1') {
                //010
                res += (i - dp[i - 1]) * (sum_0 - (i - dp[i - 1]));
            }else {
                //101
                res += dp[i - 1] * (sum_1 - dp[i - 1]);
            }
        }
        return res;
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = "001101"
     * 输出：6
     * 解释：
     * 以下下标集合是合法的：
     * - [0,2,4] ，从 "001101" 得到 "010"
     * - [0,3,4] ，从 "001101" 得到 "010"
     * - [1,2,4] ，从 "001101" 得到 "010"
     * - [1,3,4] ，从 "001101" 得到 "010"
     * - [2,4,5] ，从 "001101" 得到 "101"
     * - [3,4,5] ，从 "001101" 得到 "101"
     * 没有别的合法选择，所以总共有 6 种方法。
     * 1. 如果是“010”
     *
     * 2. 如果是“101”
     *
     *
     * ]
     * @param s
     * @return long
     * @author marks
     * @CreateDate: 2024/9/2 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(String s) {

        /**
         * 001101
         * 计算"010"
         * n = 6
         * dp[i][j][k]
         * i = [0 ~ n - 3]
         * j = [1 ~ n - 2]
         * k = [2 ~ n - 1]
         * for i and i < n - 2
         * s.charAt(i) == '0'
         *  for j = i + 1 and j < n - 1
         *  s.charAt(j) == '1'
         *      for k = j + 1 and k < n
         *      if s.charAt(k) == '1'
         *          dp[i][j][k] = dp[i][j][k - 1] + 1
         *
         * 时间复杂度O(n^3)
         * 题目s的数量级为10^5
         * 所以超时和超出内存
         * 一开始看错了, 以为是50.
         *
         */
        int n = s.length();
        long[] dp = new long[n];
        int ans = 0;
        for (int i = 0; i < n - 2; i++) {
            char char_0 = s.charAt(i);
            for (int j = i + 1; j < n - 1; j++) {
                if (s.charAt(j) != char_0) {
                    for (int k = j + 1; k < n; k++) {
                        dp[k] = dp[k - 1] + (s.charAt(k) == char_0 ? 1 : 0);
                    }
                }
                ans += dp[n - 1];
                Arrays.fill(dp, 0);
            }
        }
        return ans;
    }
}
