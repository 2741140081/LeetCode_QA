package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 11:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCR_107Test {

    @Test
    void updateMatrix() {
        int[][] mat = {{0,0,0},{0,1,0},{1,1,1}};
        LCR_107 lcr_107 = new LCR_107();
        int[][] result = lcr_107.updateMatrix(mat);
    }
}