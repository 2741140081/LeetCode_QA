package com.marks.leetcode.gird_dfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1034Test {

    @Test
    void colorBorder() {
        // grid = [[1,1,1,2,2],[2,1,2,2,2],[1,1,2,2,1]]], row = 1, col = 0, color = 1
        int[][] grid = new int[][] {{1,1,1,2,2}, {2,1,2,2,2}, {1,1,2,2,1}};
        int row = 1, col = 0, color = 1;
        int[][] result = new LeetCode_1034().colorBorder(grid, row, col, color);
    }
}