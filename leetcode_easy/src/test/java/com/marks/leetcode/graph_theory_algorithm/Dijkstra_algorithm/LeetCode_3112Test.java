package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import com.marks.utils.PrintArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/31 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3112Test {

    @Test
    void minimumTime() {
        // n = 3, edges = {{0,1,2},{1,2,1},{0,2,4}}, disappear = {1,1,5}
        int n = 3;
        int[][] edges = {{0,1,2},{1,2,1},{0,2,4}};
        int[] disappear = {1,1,5};
        int[] result = new LeetCode_3112().minimumTime(n, edges, disappear);
        new PrintArray().print(result);
    }
}