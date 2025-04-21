package com.marks.leetcode.grid_bfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1293Test {

    @Test
    void shortestPath() {
        // {{0,1,0,0,0,1,0,0},{0,1,0,1,0,1,0,1},{0,0,0,1,0,0,1,0}}
        int[][] grid = {{0,1,0,0,0,1,0,0},{0,1,0,1,0,1,0,1},{0,0,0,1,0,0,1,0}};
        int k = 1;
        int result = new LeetCode_1293().shortestPath(grid, k);
        System.out.println(result);
    }
}