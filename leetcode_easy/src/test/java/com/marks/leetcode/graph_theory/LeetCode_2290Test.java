package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2290Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/14 11:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2290Test {

    @Test
    void minimumObstacles() {
        // 输入：grid = [[0,1,1],[1,1,0],[1,1,0]]
        // 输出：2
        int[][] grid = {{0,1,1},{1,1,0},{1,1,0}};
        LeetCode_2290 leetCode_2290 = new LeetCode_2290();
        int res = leetCode_2290.minimumObstacles(grid);
        System.out.println(res);
    }
}