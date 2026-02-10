package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_313 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 14:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_313 {

    /**
     * @Description:
     * 超级丑数 是一个正整数，并满足其所有质因数都出现在质数数组 primes 中。
     * 给你一个整数 n 和一个整数数组 primes ，返回第 n 个 超级丑数 。
     * 题目数据保证第 n 个 超级丑数 在 32-bit 带符号整数范围内。
     * tips:
     * 1 <= n <= 10^5
     * 1 <= primes.length <= 100
     * 2 <= primes[i] <= 1000
     * 题目数据 保证 primes[i] 是一个质数
     * primes 中的所有值都 互不相同 ，且按 递增顺序 排列
     * @param: n
     * @param: primes
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        int result;
        result = method_01(n, primes);
        result = method_02(n, primes);
        return result;
    }

    /**
     * @Description:
     * AC: 50ms/44.04MB
     * @param: n
     * @param: primes
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[] primes) {
        long[] dp = new long[n + 1];
        int m = primes.length;
        int[] index = new int[m];
        Arrays.fill(index, 1);
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            long min = Long.MAX_VALUE;
            for (int j = 0; j < m; j++) {
                min = Math.min(min, primes[j] * dp[index[j]]);
            }
            dp[i] = min;
            // update index[]
            for (int j = 0; j < m; j++) {
                if (dp[i] == primes[j] * dp[index[j]]) {
                    index[j]++;
                }
            }
        }
        return (int) dp[n];
    }

    /**
     * @Description:
     * 1. 超时, 86/87 通过
     * 需要使用动态规划来处理
     * @param: n
     * @param: primes
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 14:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] primes) {
        Set<Long> set = new HashSet<>();
        PriorityQueue<Long> pq = new PriorityQueue<>();
        set.add(1L);
        pq.add(1L);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            long cur = pq.poll();
            ans = (int) cur;
            for (int prime : primes) {
                long next = cur * prime;
                if (set.add(next)) {
                    pq.add(next);
                }
            }
        }
        return ans;
    }


}
