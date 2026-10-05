package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3928Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/10 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3928Test {

    @Test
    void minCost() {
        int n = 2;
        int[] price = {4, 3};
        int[][] roads = {};

        int[] result = new LeetCode_3928().minCost(n, price, roads);
        System.out.println(Arrays.toString(result));

    }
}