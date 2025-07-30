package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/29 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1466Test {

    @Test
    void minReorder() {
        int n = 6;
        int[][] connections = {{0,1},{1,3},{2,3},{4,0},{4,5}};

        int result = new LeetCode_1466().minReorder(n, connections);
        System.out.println(result);
    }
}