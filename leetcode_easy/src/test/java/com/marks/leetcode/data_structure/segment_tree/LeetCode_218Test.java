package com.marks.leetcode.data_structure.segment_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_218Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/2 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_218Test {

    @Test
    void getSkyline() {
        // buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
        int[][] buildings = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        LeetCode_218 leetCode_218 = new LeetCode_218();
        System.out.println(leetCode_218.getSkyline(buildings));
    }
}