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
 * @date 2025/8/5 11:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3373Test {

    @Test
    void maxTargetNodes() {
        // 测试用例1: 题目描述中的示例
        int[][] edges1 = {{0,1},{0,2},{2,3},{2,4}};
        int[][] edges2 = {{0,1},{0,2},{0,3},{2,7},{1,4},{4,5},{4,6}};
        int[] expected1 = {8,7,7,8,8};
        int[] result1 = new LeetCode_3373().maxTargetNodes(edges1, edges2);
        assertArrayEquals(expected1, result1);

        // 测试用例2: 简单情况
        int[][] edges3 = {{0,1},{1,2},{0,3}};
        int[][] edges4 = {{0,1},{1,2},{2,3}};
        int[] result2 = new LeetCode_3373().maxTargetNodes(edges3, edges4);
        // 验证结果长度正确
        assertEquals(4, result2.length);

        // 测试用例3: 单节点树
        int[][] edges5 = {{0,1}};
        int[][] edges6 = {{0,1}};
        int[] result3 = new LeetCode_3373().maxTargetNodes(edges5, edges6);
        // 验证结果长度正确
        assertEquals(2, result3.length);

        // 测试用例4: 链式结构
        int[][] edges7 = {{0,1},{1,2},{2,3}};
        int[][] edges8 = {{0,1},{1,2}};
        int[] result4 = new LeetCode_3373().maxTargetNodes(edges7, edges8);
        // 验证结果长度正确
        assertEquals(4, result4.length);
    }
}