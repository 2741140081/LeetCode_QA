package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_357 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 10:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_357 {

    /**
     * @Description:
     * 给你一个整数 n ，统计并返回各位数字都不同的数字 x 的个数，其中 0 <= x < 10^n 。
     * tips:
     * 0 <= n <= 8
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countNumbersWithUniqueDigits(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. n > 0, 这个n就表示x的位数有n位, 例如 n = 2, 那么x的位数就是2位, x的范围就是0 ~ 99
     * AC: 0ms/41.55MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 10:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 10;
        }
        int res = 10, cur = 9;
        for (int i = 0; i < n - 1; i++) {
            cur *= 9 - i;
            res += cur;
        }
        return res;
    }

}
