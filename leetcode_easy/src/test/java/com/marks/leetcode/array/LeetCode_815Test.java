package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_815Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 10:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_815Test {

    @Test
    void numBusesToDestination() {
        // 输入：routes = [[1,2,7],[3,6,7]], source = 1, target = 6
        int[][] routes = {{1,2,7},{3,6,7}};
        int source = 1;
        int target = 6;
        LeetCode_815 leetCode_815 = new LeetCode_815();
        int result = leetCode_815.numBusesToDestination(routes, source, target);
        System.out.println(result);
    }
}