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
 * @date 2025/8/4 15:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3372Test {

    @Test
    void maxTargetNodes() {
        int[][] edges1 = {{0,1},{0,2},{0,3},{0,4}}, edges2 = {{0,1},{1,2},{2,3}};
        int k = 1;

        int[] result = new LeetCode_3372().maxTargetNodes(edges1, edges2, k);
    }
}