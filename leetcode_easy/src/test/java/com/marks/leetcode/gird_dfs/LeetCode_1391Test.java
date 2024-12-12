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
 * @date 2024/12/10 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1391Test {

    @Test
    void hasValidPath() {
        // grid = [[2,4,3],[6,5,2]]
        // [[4,1],[6,1]]
        int[][] grid = {{4,1}, {6,1}};
        boolean result = new LeetCode_1391().hasValidPath(grid);
        System.out.println(result);
    }
}