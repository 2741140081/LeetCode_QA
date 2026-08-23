package com.marks.leetcode.daily_question;

public class LeetCode_3622 {

    /**
     * 给你一个正整数 n。请判断 n 是否可以被以下两值之和 整除：
     * n 的 数字和（即其各个位数之和）。
     * n 的 数字积（即其各个位数之积）。
     * 如果 n 能被该和整除，返回 true；否则，返回 false。
     *
     * tips:
     * 1 <= n <= 10^6
     * @param n
     * @return
     */
    public boolean checkDivisibility(int n) {
        boolean result;
        result = method_01(n);
        return result;
    }

    /**
     * 模拟
     * AC: 0ms/42.04MB
     * @param n
     * @return
     */
    private boolean method_01(int n) {
        // 获取 n 的各个数位值
        int sum = 0;
        int muti = 1;
        int temp = n;
        while (temp > 0) {
            int digit = temp % 10;
            sum += digit;
            muti *= digit;
            temp /= 10;
        }
        // 获取乘积的各个数位值
        sum += muti;
        return n % sum == 0;
    }

}
