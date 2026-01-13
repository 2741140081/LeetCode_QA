package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1761Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 17:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1761Test {

    @Test
    void minTrioDegree() {
        LeetCode_1761 leetCode_1761 = new LeetCode_1761();
        // [[1,2],[4,3],[3,1],[4,2],[2,3]]
        int result = leetCode_1761.minTrioDegree(4, new int[][]{{1,2},{4,3},{3,1},{4,2},{2,3}});
        System.out.println(result);
    }
}