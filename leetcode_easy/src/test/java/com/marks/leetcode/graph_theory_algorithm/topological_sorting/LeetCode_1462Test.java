package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/27 10:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1462Test {

    @Test
    void checkIfPrerequisite() {
        int numCourses = 4;
        int[][] prerequisites = {{2, 3}, {2, 1}, {0, 3}, {0, 1}};
        int[][] queries = {{0, 1}, {0, 3}, {2, 3}, {3, 0}, {2, 0}, {0, 2}};
        List<Boolean> result = new LeetCode_1462().checkIfPrerequisite(numCourses, prerequisites, queries);
        System.out.println(result.stream().toString());
    }
}