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
 * @date 2025/8/4 18:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2467Test {

    @Test
    void mostProfitablePath() {
//        int[][] edges = {{0,1},{1,2},{1,3},{3,4}};
//        int[] amount = {-2,4,2,-4,6};
//        int bob = 3;
        int[][] edges = {{0,2},{0,5},{1,3},{1,5},{2,4}};
        int bob = 4;
        int[] amount = {5018,8388,6224,3466,3808,3456};
        int result = new LeetCode_2467().mostProfitablePath(edges, bob, amount);
    }
}