package com.marks.leetcode.knapsack.complete_knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1449Test {

    @Test
    void largestNumber() {
        // cost = [4,3,2,5,6,7,2,5,5], target = 9
        int[] cost = new int[] {4,3,2,5,6,7,2,5,5};
        int target = 9;
        String result = new LeetCode_1449().largestNumber(cost, target);
        System.out.println(result);
    }
}