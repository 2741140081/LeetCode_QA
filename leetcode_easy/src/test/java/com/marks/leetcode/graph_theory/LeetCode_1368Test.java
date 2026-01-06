package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1368Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1368Test {

    @Test
    void minCost() {
        // 输入：grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
        // 输出：3
//        int[][] grid = {{1, 1, 1, 1}, {2, 2, 2, 2}, {1, 1, 1, 1}, {2, 2, 2, 2}};

        // 输入：grid = [[1,1,3],[3,2,2],[1,1,4]]
        // 输出：0
        int[][] grid = {{1, 1, 3}, {3, 2, 2}, {1, 1, 4}};
        LeetCode_1368 leetCode_1368 = new LeetCode_1368();
        System.out.println(leetCode_1368.minCost(grid));
    }
}