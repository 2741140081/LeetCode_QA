package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_343 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/24 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_343 {

    /**
     * @Description:
     * 给定一个正整数 n ，将其拆分为 k 个 正整数 的和（ k >= 2 ），并使这些整数的乘积最大化。
     *
     * 返回 你可以获得的最大乘积 。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int integerBreak(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * n = 10, 3 * 3 * 4 = 36, 最大乘积为36
     * n = 5, 2 * 3 = 6
     * n = 6, 2 * 2 * 2 = 8, 3 * 3 = 9
     * n = 7, 2 * 2 * 3 = 12, 3 * 4 = 12
     * n = 8, 2 * 2 * 2 * 2 = 16, 3 * 3 * 2 = 18, 4 * 4 = 16
     * n = 9, 2 * 2 * 2 * 3 = 24, 3 * 3 * 3 = 27, 4 * 5 = 20
     * 根据以上规律, 可以看出只有分解为3时乘积最大
     * AC: 0ms/41.61MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        if (n <= 3) {
            return n - 1;
        } else if (n == 4) {
            return 4;
        }
        int ans = 1;
        while (n > 4) {
            ans *= 3;
            n -= 3;
        }
        return ans * n;
    }
}
