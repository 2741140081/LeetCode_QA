package com.marks.leetcode.state_machine_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/27 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_714Test {

    @Test
    void maxProfit() {
        // prices = [1, 3, 2, 8, 4, 9], fee = 2
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;
        int result = new LeetCode_714().maxProfit(prices, fee);
        System.out.println(result);
    }
}