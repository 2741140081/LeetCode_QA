package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3286Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3286Test {

    @Test
    void findSafeWalk() {
        // grid = [[1,1,1,1],[1,0,0,0],[1,0,0,1],[0,0,1,1]], health = 6
        List<List<Integer>> grid = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        grid.add(list);
        int health = 4;
        LeetCode_3286 leetCode3286 = new LeetCode_3286();
//        boolean result = leetCode3286.findSafeWalk(grid, health);
//        System.out.println(result);

        List<List<Integer>> grid2 = new ArrayList<>();
        grid2.add(List.of(1, 1, 1, 1));
        grid2.add(List.of(1, 0, 0, 0));
        grid2.add(List.of(1, 0, 0, 1));
        grid2.add(List.of(0, 0, 1, 1));
        int health2 = 6;
        boolean result2 = leetCode3286.findSafeWalk(grid2, health2);
        System.out.println("Test case 2 result: " + result2);
    }
}