package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3870 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3870 {

    /**
     * @Description:
     * 给你一个整数 n。
     * 返回将所有从 [1, n]（包含两端）范围内的整数以 标准 数字格式书写时所用到的 逗号总数。
     * 在 标准 格式中：
     * 从右边开始，每 三位 数字后插入一个逗号。
     * 位数 少于四位 的数字不包含逗号。
     *
     * tips:
     * 1 <= n <= 10^5
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/09/08 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countCommas(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    // AC: 0ms/41.68MB
    private int method_01(int n) {
        if (n < 1000) {
            return 0;
        }
        return n - 999;
    }

}
