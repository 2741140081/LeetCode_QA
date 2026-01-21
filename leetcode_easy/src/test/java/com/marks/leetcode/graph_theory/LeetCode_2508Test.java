package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2508Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2508Test {

    @Test
    void isPossible() {
        // [[5,9],[8,1],[2,3],[7,10],[3,6],[6,7],
        // [7,8],[5,1],[5,7],[10,11],[3,7],[6,11],
        // [8,11],[3,4],[8,9],[9,1],[2,10],[9,11],
        // [5,11],[2,5],[8,10],[2,7],[4,1],[3,10],
        // [6,1],[4,9],[4,6],[4,5],[2,4],[2,11],
        // [5,8],[6,9],[4,10],[3,11],[4,7],[3,5],
        // [7,1],[2,9],[6,10],[10,1],[5,6],[3,9],
        // [2,6],[7,9],[4,11],[4,8],[6,8],[3,8],
        // [9,10],[5,10],[2,8],[7,11]]
        LeetCode_2508 leetCode_2508 = new LeetCode_2508();
        System.out.println(leetCode_2508.isPossible(20,
                List.of(List.of(5, 9), List.of(8, 1), List.of(2, 3), List.of(7, 10), List.of(3, 6), List.of(6, 7),
                        List.of(7, 8), List.of(5, 1), List.of(5, 7), List.of(10, 11), List.of(3, 7), List.of(6, 11),
                        List.of(8, 11), List.of(3, 4), List.of(8, 9), List.of(9, 1), List.of(2, 10), List.of(9, 11),
                        List.of(5, 11), List.of(2, 5), List.of(8, 10), List.of(2, 7), List.of(4, 1), List.of(3, 10),
                        List.of(6, 1), List.of(4, 9), List.of(4, 6), List.of(4, 5), List.of(2, 4), List.of(2, 11),
                        List.of(5, 8), List.of(6, 9), List.of(4, 10), List.of(3, 11), List.of(4, 7), List.of(3, 5),
                        List.of(7, 1), List.of(2, 9), List.of(6, 10), List.of(10, 1), List.of(5, 6), List.of(3, 9),
                        List.of(2, 6), List.of(7, 9), List.of(4, 11), List.of(4, 8), List.of(6, 8), List.of(3, 8),
                        List.of(9, 10), List.of(5, 10), List.of(2, 8), List.of(7, 11))
                )
        );
        // 测试2: [[4,1],[3,2],[2,1],[3,4],[1,5],[2,5],[4,6],[3,6]]
        List<List<Integer>> edges = List.of(List.of(4, 1), List.of(3, 2), List.of(2, 1), List.of(3, 4), List.of(1, 5),
                List.of(2, 5), List.of(4, 6), List.of(3, 6));
        System.out.println(leetCode_2508.isPossible(6, edges));
        // 1,2,3,4;
    }
}