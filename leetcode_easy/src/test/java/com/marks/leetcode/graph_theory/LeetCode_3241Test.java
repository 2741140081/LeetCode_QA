package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3241Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3241Test {

    @Test
    void timeTaken() {
        // 输入：edges = [[1,0],[5,2],[7,1],[6,3],[3,1],[4,1],[8,7],[2,0]]
        // 输出：[4,5,6,6,6,8,7,6,7]
        int[][] edges = {{1, 0}, {5, 2}, {7, 1}, {6, 3}, {3, 1}, {4, 1}, {8, 7}, {2, 0}};
        int[] result = new LeetCode_3241().timeTaken(edges);
    }
}