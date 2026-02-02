package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3651Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 11:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3651Test {

    @Test
    void minCost() {
        // 输入: grid = [[1,3,3],[2,5,4],[4,3,5]], k = 2
        // 输出: 7
        int[][] grid1 = {{1, 3, 3}, {2, 5, 4}, {4, 3, 5}};
        int k1 = 2;
        LeetCode_3651 lc = new LeetCode_3651();
        int r1 = lc.minCost(grid1, k1);
        System.out.println(r1);
    }
}