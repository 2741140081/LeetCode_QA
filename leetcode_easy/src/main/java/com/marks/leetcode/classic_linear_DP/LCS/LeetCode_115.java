package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_115 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数，结果需要对 10^9 + 7 取模。
     * ]
     * @param s
     * @param t
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 16:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numDistinct(String s, String t) {
        int result = 0;
        result = method_01(s, t);
        return result;
    }

    private int method_01(String s, String t) {
        return 0;
    }
}
