package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3007 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 16:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3007 {

    /**
     * @Description:
     * 给你一个整数 k 和一个整数 x 。整数 num 的价值是它的二进制表示中在 x，2x，3x 等位置处 设置位 的数目（从最低有效位开始）。
     * 下面的表格包含了如何计算价值的例子。
     *
     * @param: k
     * @param: x
     * @return long
     * @author marks
     * @CreateDate: 2026/03/05 16:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long findMaximumNumber(long k, int x) {
        long result;
        result = method_01(k, x);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：k = 9, x = 1
     * 输出：6
     * 1. 方式一, 直接暴力求解, 从1开始遍历计算
     * @param: k
     * @param: x
     * @return long
     * @author marks
     * @CreateDate: 2026/03/05 16:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(long k, int x) {

        return 0;
    }

}
