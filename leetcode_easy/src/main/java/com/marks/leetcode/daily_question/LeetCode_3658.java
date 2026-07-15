package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3658 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/15 9:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3658 {

    public int gcdOfOddEvenSums(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 计算前 n 个奇数和, (1 + (1 + 2 * (n - 1))) * n / 2; 偶数和 (2 + 2 * n) * n / 2
     * AC: 0ms/41.8MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/07/15 9:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int oddSum = n * n;
        int evenSum = (n + 1) * n;
        return gcd(oddSum, evenSum);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
