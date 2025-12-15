package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1192Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1192Test {

    @Test
    void criticalConnections() {
        int n = 6;
        int[][] connections = {{0, 1}, {1, 2}, {2, 0}, {1, 3}, {3, 4}, {4, 5}, {5, 3}};
        // 将connections转换为List<List<Integer>>
        List<List<Integer>> connectionList = Arrays.stream(connections)
                .map(conn -> Arrays.stream(conn).boxed().collect(Collectors.toList()))
                .toList();
        LeetCode_1192 leetCode_1192 = new LeetCode_1192();
        List<List<Integer>> ans = leetCode_1192.criticalConnections(n, connectionList);
    }

    @Test
    void criticalConnections2() {
        int n = 7;
        int[][] connections = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 0}, {3, 6}};
        // 将connections转换为List<List<Integer>>
        List<List<Integer>> connectionList = Arrays.stream(connections)
                .map(conn -> Arrays.stream(conn).boxed().collect(Collectors.toList()))
                .toList();
        LeetCode_1192 leetCode_1192 = new LeetCode_1192();
        List<List<Integer>> ans = leetCode_1192.criticalConnections(n, connectionList);
    }
}