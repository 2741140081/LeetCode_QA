package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/24 17:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_210Test {

    @Test
    void findOrder() {
        // numCourses = 3, prerequisites = [[1,0]]
        int numCourses = 3;
        int[][] prerequisites = {{2, 1}, {1, 0}};
        int[] result = new LeetCode_210().findOrder(numCourses, prerequisites);
    }
}