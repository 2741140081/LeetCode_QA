package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_764Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 14:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_764Test {

    @Test
    void orderOfLargestPlusSign() {
        // 输入: n = 5, mines = [[4, 2]]
        //输出: 2
        int n = 5;
        int[][] mines = {{4, 2}};
        int result = new LeetCode_764().orderOfLargestPlusSign(n, mines);
        System.out.println(result);
    }
}