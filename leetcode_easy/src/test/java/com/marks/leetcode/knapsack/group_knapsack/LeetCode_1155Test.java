package com.marks.leetcode.knapsack.group_knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 16:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1155Test {

    @Test
    void numRollsToTarget() {
        // n = 2, k = 6, target = 7
        int n = 2;
        int k = 6;
        int target = 7;
        int result = new LeetCode_1155().numRollsToTarget(n, k, target);
        System.out.println(result);
    }
}