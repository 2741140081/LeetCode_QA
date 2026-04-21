package com.marks.leetcode.DP_II;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2920Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/20 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2920Test {

    @Test
    void maximumPoints() {
        // edges = [[0,1],[1,2],[2,3]], coins = [10,10,3,3], k = 5
        int[][] edges = {{0,1},{1,2},{2,3}};
        int[] coins = {10,10,3,3};
        int k = 5;
        LeetCode_2920 leetCode_2920 = new LeetCode_2920();
        int res = leetCode_2920.maximumPoints(edges, coins, k);
        System.out.println(res);
    }
}