package com.marks.leetcode.bfs_algorithm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_133Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_133Test {

    @Test
    void cloneGraph() {
        LeetCode_133 leetCode_133 = new LeetCode_133();
        // adjList = [[2,4],[1,3],[2,4],[1,3]]
        LeetCode_133.Node node1 = new LeetCode_133.Node();
        node1.val = 1;
        node1.neighbors = new ArrayList<>();
        LeetCode_133.Node node2 = new LeetCode_133.Node();
        node2.val = 2;
        node2.neighbors = new ArrayList<>();
        LeetCode_133.Node node3 = new LeetCode_133.Node();
        node3.val = 3;
        node3.neighbors = new ArrayList<>();
        LeetCode_133.Node node4 = new LeetCode_133.Node();
        node4.val = 4;
        node4.neighbors = new ArrayList<>();
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node3.neighbors.add(node4);
        node4.neighbors.add(node1);
        node4.neighbors.add(node3);
        LeetCode_133.Node result = leetCode_133.cloneGraph(node1);


    }
}