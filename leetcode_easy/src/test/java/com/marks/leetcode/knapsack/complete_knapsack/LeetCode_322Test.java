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
 * @data 2024/8/6 16:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_322Test {

    @Test
    void coinChange() {
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        int result = new LeetCode_322().coinChange(coins, amount);
        System.out.println(result);
    }
}