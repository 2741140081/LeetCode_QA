package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1139Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25 10:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1139Test {

    @Test
    void largest1BorderedSquare() {
        // 输入：grid = [[1,1,1],[1,0,1],[1,1,1]]
        // 输出：9
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
//        int[][] grid = {{1,1,0,0}};
        LeetCode_1139 leetCode1139 = new LeetCode_1139();
        int result = leetCode1139.largest1BorderedSquare(grid);
        System.out.println(result);
    }
}