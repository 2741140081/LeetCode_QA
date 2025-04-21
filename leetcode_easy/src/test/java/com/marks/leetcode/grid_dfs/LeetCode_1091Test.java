package com.marks.leetcode.grid_dfs;

import com.marks.leetcode.grid_bfs.LeetCode_1091;
import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/13 15:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1091Test {

    @Test
    void shortestPathBinaryMatrix() {
        int[][] grid = {{0,0,0},{1,1,0},{1,1,0}};
        int result = new LeetCode_1091().shortestPathBinaryMatrix(grid);
        System.out.println(result);
    }
}