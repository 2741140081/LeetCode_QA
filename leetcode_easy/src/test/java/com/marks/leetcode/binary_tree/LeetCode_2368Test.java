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
 * @date 2025/7/25 16:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2368Test {

    @Test
    void reachableNodes() {
        int n = 7;
        int[][] edges = {{0,1},{1,2},{3,1},{4,0},{0,5},{5,6}};
        int[] restricted = {4,5};

        int result = new LeetCode_2368().reachableNodes(n, edges, restricted);

    }
}