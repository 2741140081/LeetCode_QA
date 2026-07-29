package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1620Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/29 17:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1620Test {

    @Test
    void bestCoordinate() {
        // [[30,34,31],[10,44,24],[14,28,23],[50,38,1],[40,13,6],[16,27,9],[2,22,23],[1,6,41],[34,22,40],[40,10,11]]
        int[][] towers = new int[][]{{30,34,31},{10,44,24},{14,28,23},{50,38,1},{40,13,6},{16,27,9},{2,22,23},{1,6,41},{34,22,40},{40,10,11}};
        int radius = 20;
        LeetCode_1620 leetCode1620 = new LeetCode_1620();
        int[] result = leetCode1620.bestCoordinate(towers, radius);
        System.out.println(Arrays.toString(result));
    }
}