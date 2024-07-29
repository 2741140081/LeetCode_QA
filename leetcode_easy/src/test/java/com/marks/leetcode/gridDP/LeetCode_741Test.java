package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 14:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_741Test {

    @Test
    void cherryPickup() {
//        int[][] grid = new int[][]{{0,1,-1}, {1,0,-1}, {1,1,1}};
//        int[][] grid = new int[][]{{1,1,-1}, {1,-1,1}, {-1,1,1}};
        // [[1,1,1,1,0,0,0],[0,0,0,1,0,0,0],[0,0,0,1,0,0,1],[1,0,0,1,0,0,0],[0,0,0,1,0,0,0],[0,0,0,1,0,0,0],[0,0,0,1,1,1,1]]
        int[][] grid = new int[][]{{1,1,1,1,0,0,0}, {0,0,0,1,0,0,0}, {0,0,0,1,0,0,1}, {1,0,0,1,0,0,0},
                {0,0,0,1,0,0,0}, {0,0,0,1,0,0,0}, {0,0,0,1,1,1,1}};
        int result = new LeetCode_741().cherryPickup(grid);
        System.out.println(result);
    }
}