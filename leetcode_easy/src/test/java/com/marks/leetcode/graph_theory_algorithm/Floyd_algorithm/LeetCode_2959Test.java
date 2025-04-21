package com.marks.leetcode.graph_theory_algorithm.Floyd_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2959Test {

    @Test
    void numberOfSets() {
        // 输入：n = 3, maxDistance = 5, roads = {{0,1,2},{1,2,10},{0,2,10}}
        int n = 3, maxDistance = 5;
        int[][] roads = {{0,1,2},{1,2,10},{0,2,10}};
        int result = new LeetCode_2959().numberOfSets(n, maxDistance, roads);
        System.out.println(result);
    }
}