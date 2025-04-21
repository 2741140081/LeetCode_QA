package com.marks.leetcode.graph_theory_algorithm.dfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/18 16:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2492Test {

    @Test
    void minScore() {
        int n = 18;
        int[][] roads = {{8,5,6357},{8,12,979},{1,14,7145},{11,16,1109},{11,2,1214},{8,14,6543},{17,13,7881},{12,18,9162},{14,5,6548},{2,16,8107},{6,13,4204},{17,6,7215},{18,8,6943},{8,1,9279},{5,12,5929}};
        int result = new LeetCode_2492().minScore(n, roads);
        System.out.println(result);
    }
}