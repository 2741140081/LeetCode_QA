package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/14 16:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2571 {

    /**
     * @Description:
     * 给你一个正整数 n ，你可以执行下述操作 任意 次：
     *
     * n 加上或减去 2 的某个 幂
     * 返回使 n 等于 0 需要执行的 最少 操作数。
     *
     * 如果 x == 2^i 且其中 i >= 0 ，则数字 x 是 2 的幂。
     * tips:
     * 1 <= n <= 10^5
     *
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 16:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * 不能直接 return Integer.bitCount(n); 需要考虑加法情况
     * 1. dp[i] = dp[i - 1] + 1， 111 + 1 = 1000， 101 + 1 = 110
     * 2. 11 + 1 = 100,100 => 0, 101 => 2
     * AC: 0ms/39.69MB
     * @param n 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 16:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int result = 0;
        while (n != 0) {
            int bit = 1;
            while ((n & bit) == 0) {
                bit <<= 1;
            }
            if (Integer.bitCount(n) > Integer.bitCount(n + bit)) {
                n += bit;
            } else {
                n -= bit;
            }
            result++;
        }

        return result;
    }

}
