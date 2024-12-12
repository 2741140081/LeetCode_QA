package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 16:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_878 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 一个正整数如果能被 a 或 b 整除，那么它是神奇的。
     *
     * 给定三个整数 n , a , b ，返回第 n 个神奇的数字。因为答案可能很大，所以返回答案 对 109 + 7 取模 后的值。
     *
     * tips:
     * 1 <= n <= 10^9
     * 2 <= a, b <= 4 * 10^4
     * ]
     * @param n 
     * @param a 
     * @param b 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 16:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int nthMagicalNumber(int n, int a, int b) {
        int result;
        result = method_01(n, a, b);
        return result;
    }

    /**
     * @Description: [
     * 输入：n = 4, a = 2, b = 3
     * 输出：6
     * left = 2, right = 12, mid = 7, 7 / 2 = 3(2, 4, 6), 7 / 3 = 2(3, 6), sum = 3 + 2 = 5 > n
     * left = 2, right = 7, mid = 4, 4 / 2 = 2, 4 / 3 = 1, sum = 3 < n
     * left = 5, right = 7, mid = 6, 6 / 2 = 3, 6 / 3 = 2, sum = 5 = n
     * left = 5, right = 6, mid = 5, sum = 3 < n
     * left = 6, right = 6, break, return left
     *
     * 查看题解后解答:容斥原理 + 二分查找
     * 容斥原理:‌容斥原理‌是一种在计数时避免重复和遗漏的数学原理。其基本思想是：
     * 先不考虑重叠的情况，把包含于某内容中的所有对象的数目先计算出来，然后再把计数时重复计算的数目排斥出去，使得计算的结果既无遗漏又无重
     * count = (mid / a) + (mid / b) - (mid / c);
     * AC:0ms/39.55MB
     * ]
     * @param n
     * @param a
     * @param b
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int a, int b) {
        long left = Math.min(a, b);
        long right = (long)n * Math.min(a, b);
        // c 为 a, b的最小公倍数, 方法为c = a * b / (最大公约数)
        int c = lcm(a, b);
        while (left < right) {
            long mid = (right - left) / 2 + left;
            long count = mid / a + mid / b - mid / c;
            if (count >= n) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return (int) (left % MOD);
    }

    /**
     * @Description: [a, b最小公倍数]
     * @param a
     * @param b
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * @Description: [a, b 最大公约数]
     * @param a
     * @param b
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }
}
