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
 * @data 2024/7/26 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1463Test {

    @Test
    void cherryPickup() {
        // grid = [[1,0,0,0,0,0,1],[2,0,0,0,0,3,0],[2,0,9,0,0,0,0],[0,3,0,5,4,0,0],[1,0,2,3,0,0,6]]
//        int[][] grid = new int[][]{
//                {1,0,0,0,0,0,1},
//                {2,0,0,0,0,3,0},
//                {2,0,9,0,0,0,0},
//                {0,3,0,5,4,0,0},
//                {1,0,2,3,0,0,6}};
        // [[3,1,1],[2,5,1],[1,5,5],[2,1,1]]
        int[][] grid = new int[][]{{3,10,1},{2,5,1},{1,5,5},{2,1,1}};
        int result = new LeetCode_1463().cherryPickup(grid);
        System.out.println(result);
    }
}