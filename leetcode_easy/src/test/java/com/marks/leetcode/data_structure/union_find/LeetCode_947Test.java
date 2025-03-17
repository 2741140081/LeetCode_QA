package com.marks.leetcode.data_structure.union_find;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/17 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_947Test {

    @Test
    void removeStones() {
        // stones = {{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}}
        int[][] stones = {{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}};
        int result = new LeetCode_947().removeStones(stones);
        System.out.println(result);
    }
}