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
 * @date 2025/8/4 15:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3067Test {

    @Test
    void countPairsOfConnectableServers() {
//        int[][] edges = {{0,1,1},{1,2,5},{2,3,13},{3,4,9},{4,5,2}};
//        int signalSpeed = 1;
        int[][] edges = {{0,6,3},{6,5,3},{0,3,1},{3,2,7},{3,1,6},{3,4,2}};
        int signalSpeed = 3;
        int[] result = new LeetCode_3067().countPairsOfConnectableServers(edges, signalSpeed);
    }
}