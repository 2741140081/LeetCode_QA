package com.marks.leetcode.partition_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1416 {
    private final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [
     * 某个程序本来应该输出一个整数数组。但是这个程序忘记输出空格了以致输出了一个数字字符串，我们所知道的信息只有：
     * 数组中所有整数都在 [1, k] 之间，且数组中的数字都没有前导 0 。
     *
     * 给你字符串 s 和整数 k 。可能会有多种不同的数组恢复结果。
     * 按照上述程序，请你返回所有可能输出字符串 s 的数组方案数。
     *
     * 由于数组方案数可能会很大，请你返回它对 10^9 + 7 取余 后的结果。
     * tips:
     * 1 <= s.length <= 10^5.
     * s 只包含数字且不包含前导 0 。
     * 1 <= k <= 10^9
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfArrays(String s, int k) {
        int result = 0;
//        result = method_01(s, k);
        result = method_02(s, k);
        return result;
    }

    /**
     * @Description: [
     * 基于method_01和官方题解得出
     * AC:39ms/44.1MB
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s, int k) {
        int n = s.length();
        long kLong = k;
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            long num = 0, base = 1;
            for (int j = i - 1; j >= 0 && i - j <= 10; j--) {
                num += (s.charAt(j) - '0') * base;
                if (num > kLong) {
                    break;
                }
                if (s.charAt(j) != '0') {
                    dp[i] = (dp[i] + dp[j]) % MOD;
                }
                base *= 10;
            }
        }
        return (int) dp[n];
    }

    /**
     * @Description: [
     * 输入：s = "1317", k = 2000
     * 输出：8
     * 解释：可行的数组方案为 [1317]，[131,7]，[13,17]，[1,317]，[13,1,7]，[1,31,7]，[1,3,17]，[1,3,1,7]
     *
     * 还行, 磕磕绊绊还是写出来了, 虽然不是很完美, 复杂度有点高
     * AC:313ms/47.57MB
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        long kLong = k;
        int len = String.valueOf(k).length();
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (i - j > len) {
                    break;
                }
                if (s.charAt(j) != '0' && Long.valueOf(s.substring(j, i)) <= kLong) {
                    dp[i] = (dp[i] + dp[j]) % MOD;
                }
            }
        }
        return (int) dp[n];
    }

}
