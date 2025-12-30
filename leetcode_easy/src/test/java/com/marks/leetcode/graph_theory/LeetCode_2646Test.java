package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2646Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/30 16:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2646Test {

    @Test
    void minimumTotalPrice() {
        // n = 4, edges = [[0,1],[1,2],[1,3]], price = [2,2,10,6], trips = [[0,3],[2,1],[2,3]]
        //输出：23
        int n = 4;
        int[][] edges = {{0, 1}, {1, 2}, {1, 3}};
        int[] price = {2, 2, 10, 6};
        int[][] trips = {{0, 3}, {2, 1}, {2, 3}};
        int result = new LeetCode_2646().minimumTotalPrice(n, edges, price, trips);
        assertEquals(23, result);
    }
}