package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_56Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 16:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_56Test {

    @Test
    void conveyorBelt() {
        // 输入：matrix = [">^^>","<^v>","^v^<"], start = [0,0], end = [1,3]
        // 输出：3
        String[] matrix = new String[3];
        matrix[0] = ">^^>";
        matrix[1] = "<^v>";
        matrix[2] = "^v^<";
        int[] start = new int[]{0, 0};
        int[] end = new int[]{1, 3};
        int result = new LCP_56().conveyorBelt(matrix, start, end);
        System.out.println(result);
    }
}