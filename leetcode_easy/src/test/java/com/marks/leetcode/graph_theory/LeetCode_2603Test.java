package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2603Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/30 14:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2603Test {

    @Test
    void collectTheCoins() {
        // 输入：coins = [0,0,0,1,1,0,0,1], edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[5,6],[5,7]]
        int[] coins = {0,0,0,1,1,0,0,1};
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{2,5},{5,6},{5,7}};
        LeetCode_2603 leetCode_2603 = new LeetCode_2603();
        int result = leetCode_2603.collectTheCoins(coins, edges);
        System.out.println(result);
    }
}