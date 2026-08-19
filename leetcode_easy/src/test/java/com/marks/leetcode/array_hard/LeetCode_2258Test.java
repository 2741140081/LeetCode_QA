package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2258Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/19 11:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2258Test {

    @Test
    void maximumMinutes() {
        // 输入：grid = [[0,2,0,0,0,0,0],[0,0,0,2,2,1,0],[0,2,0,0,1,2,0],[0,0,2,2,2,0,2],[0,0,0,0,0,0,0]]
        int[][] grid = {{0,2,0,0,0,0,0},{0,0,0,2,2,1,0},{0,2,0,0,1,2,0},{0,0,2,2,2,0,2},{0,0,0,0,0,0,0}};
        // 输入：grid = [[0,0,0],[2,2,0],[1,2,0]]
        grid = new int[][]{{0,0,0},{2,2,0},{1,2,0}};
        // 输入：grid = [[0,2,0,0,1],[0,2,0,2,2],[0,2,0,0,0],[0,0,2,2,0],[0,0,0,0,0]]
        grid = new int[][]{{0,2,0,0,1},{0,2,0,2,2},{0,2,0,0,0},{0,0,2,2,0},{0,0,0,0,0}};
        LeetCode_2258 leetCode2258 = new LeetCode_2258();
        int result = leetCode2258.maximumMinutes(grid);
        System.out.println(result);
    }
}