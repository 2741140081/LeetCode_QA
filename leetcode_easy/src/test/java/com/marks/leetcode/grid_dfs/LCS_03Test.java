package com.marks.leetcode.grid_dfs;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCS_03Test {

    @Test
    void largestArea() {
        // grid = ["11111100000","21243101111","21224101221","11111101111"]
        String[] grid = {"11111100000","21243101111","21224101221","11111101111"};
        int result = new LCS_03().largestArea(grid);
        System.out.println(result);
    }
}