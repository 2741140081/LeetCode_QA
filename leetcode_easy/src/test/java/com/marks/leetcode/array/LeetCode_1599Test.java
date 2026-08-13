package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1599Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/29 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1599Test {

    @Test
    void minOperationsMaxProfit() {
        // customers = [8,3], boardingCost = 5, runningCost = 6
        int[] customers = {2};
        int boardingCost = 2, runningCost = 4;
        LeetCode_1599 leetCode1599 = new LeetCode_1599();
        int result = leetCode1599.minOperationsMaxProfit(customers, boardingCost, runningCost);
        System.out.println(result);
    }
}