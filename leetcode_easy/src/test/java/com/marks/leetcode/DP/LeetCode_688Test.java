package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_688Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/11 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_688Test {

    @Test
    void knightProbability() {
        // 输入: n = 3, k = 2, row = 0, column = 0
        //输出: 0.0625
        LeetCode_688 leetCode_688 = new LeetCode_688();
        double res = leetCode_688.knightProbability(3, 3, 0, 0);
        System.out.println(res);
    }
}