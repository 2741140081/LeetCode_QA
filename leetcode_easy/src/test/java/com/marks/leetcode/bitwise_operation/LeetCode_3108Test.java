package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/26 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3108Test {

    @Test
    void minimumCost() {
        // edges = [[0,1,7],[1,3,7],[1,2,1]], query = [[0,3],[3,4]]
        int n = 5;
        int[][] edges = {{0,1,7}, {1,3,7}, {1,2,1}};
        int[][] query = {{0, 3}, {3,4}};
        LeetCode_3108 leetCode_3108 = new LeetCode_3108();
        int[] ans = leetCode_3108.minimumCost(n, edges, query);
        System.out.println(ans);
        assertArrayEquals(new int[]{1,-1}, ans);
    }
}