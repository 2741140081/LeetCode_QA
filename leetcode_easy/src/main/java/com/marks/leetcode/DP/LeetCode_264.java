package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_264 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_264 {

    /**
     * @Description:
     * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
     * 丑数 就是质因子只包含 2、3 和 5 的正整数。
     * tips:
     * 1 <= n <= 1690
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int nthUglyNumber(int n) {
        int result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }

    /**
     * @Description:
     * 查看官方题解, 使用动态规划来解决
     * AC: 4ms/43.56MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n) {
        int[] nums = {2, 3, 5};
        int m = nums.length;
        long[] dp = new long[n + 1];
        dp[1] = 1;
        int[] index = new int[m];
        Arrays.fill(index, 1);
        for (int i = 2; i <= n; i++) {
            long min = Long.MAX_VALUE;
            for (int j = 0; j < m; j++) {
                min = Math.min(min, nums[j] * dp[index[j]]);
            }
            dp[i] = min;
            // update index[]
            for (int j = 0; j < m; j++) {
                if (nums[j] * dp[index[j]] == min) {
                    index[j]++;
                }
            }
        }
        return (int) dp[n];
    }

    /**
     * @Description:
     * [1, 2, 3, 4, 5, 6, 8, 9, 10, 12]
     * 1. 可以用类似于这种 int[] nums = [2,3,4,5];
     * 2, 3, 2*2, 5, 2*3, 2*2*2, 3*3, 2*5, 2*2*3, 3*5, 2*2*2*2, 2*3*3, 2*2*5, 2*2*2*3, 5*5, 3*3*3;
     * AC: 58ms/45.66MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[] nums = {2, 3, 5};
        Set<Long> set = new HashSet<>();
        PriorityQueue<Long> pq = new PriorityQueue<>();
        set.add(1L);
        pq.add(1L);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            long x = pq.poll();
            ans = (int) x;
            for (int num : nums) {
                long next = x * num;
                if (set.add(next)) {
                    pq.add(next);
                }
            }
        }
        return ans;
    }

}
