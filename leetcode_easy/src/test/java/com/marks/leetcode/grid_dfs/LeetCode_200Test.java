package com.marks.leetcode.grid_dfs;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: {类型描述} </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/6 17:47
 * @update {序号}{日期YYYY-MM-DD} {更改人姓名}{变更描述}
 */
class LeetCode_200Test {

    @Test
    void numIslands() {
        char[][] grid = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        int result = new LeetCode_200().numIslands(grid);
        System.out.println(result);
    }
}