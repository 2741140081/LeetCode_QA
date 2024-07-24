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
 * @data 2024/7/24 16:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2328Test {

    @Test
    void countPaths() {
        int[][] grid = new int[][]{{1, 1},{3, 4}};
        int result = new LeetCode_2328().countPaths(grid);
        System.out.println(result);
    }
}