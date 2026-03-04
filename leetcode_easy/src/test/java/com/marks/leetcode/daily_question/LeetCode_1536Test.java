package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1536Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/2 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1536Test {

    @Test
    void minSwaps() {
        // 输入：grid = [[0,0,1],[1,1,0],[1,0,0]]
        //输出：3
        int[][] grid = {{0, 0, 1}, {1, 1, 0}, {1, 0, 0}};
        int result = new LeetCode_1536().minSwaps(grid);
        System.out.println(result);
    }
    @Test
    void minSwaps02() {
        // 输入：grid = [[0,0],[0,1]]
        //输出：0
        int[][] grid = {{0, 0}, {0, 1}};
        int result = new LeetCode_1536().minSwaps(grid);
        System.out.println(result);
    }
}