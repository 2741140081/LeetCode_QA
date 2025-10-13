package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/11 15:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2801 {
    private static final int MOD = 1000000007;

    /**
     * @Description:
     * 给你两个正整数 low 和 high ，都用字符串表示，请你统计闭区间 [low, high] 内的 步进数字 数目。
     * 如果一个整数相邻数位之间差的绝对值都 恰好 是 1 ，那么这个数字被称为 步进数字 。
     * 请你返回一个整数，表示闭区间 [low, high] 之间步进数字的数目。
     * 由于答案可能很大，请你将它对 10^9 + 7 取余 后返回。
     * 注意：步进数字不能有前导 0 。
     *
     * tips:
     * 1 <= int(low) <= int(high) < 10^100
     * 1 <= low.length, high.length <= 100
     * low 和 high 只包含数字。
     * low 和 high 都不含前导 0 。
     * @param low
     * @param high
     * @return int
     * @author marks
     * @CreateDate: 2025/10/11 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSteppingNumbers(String low, String high) {
        int result;
        result = method_01(low, high);
        return result;
    }


    /**
     * @Description:
     * 1. 用回溯法试试看
     * @param low
     * @param high
     * @return int
     * @author marks
     * @CreateDate: 2025/10/11 15:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int ans = 0;
    private int method_01(String low, String high) {

        return ans;
    }

}
