package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3425Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/7 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3425Test {

    @Test
    void longestSpecialPath() {
        // 输入：edges = [[0,1,2],[1,2,3],[1,3,5],[1,4,4],[2,5,6]], nums = [2,1,2,1,3,1]
        int[][] edges = {{0,1,2},{1,2,3},{1,3,5},{1,4,4},{2,5,6}};
        int[] nums = {2,1,2,1,3,1};
        // [[2,0,6],[0,1,3],[1,3,7]]
        // [4,1,4,1]
        edges = new int[][]{{2, 0, 6}, {0, 1, 3}, {1, 3, 7}};
        nums = new int[]{4, 1, 4, 1};

        // [[1,2,5],[3,2,7],[2,0,5],[4,0,1]]
        // [1,5,5,3,1]
        edges = new int[][]{{1,2,5},{3,2,7},{2,0,5},{4,0,1}};
        nums = new int[]{1,5,5,3,1};

        int[] result = new LeetCode_3425().longestSpecialPath(edges, nums);
        // sout result
        System.out.println(Arrays.toString(result));
    }
}