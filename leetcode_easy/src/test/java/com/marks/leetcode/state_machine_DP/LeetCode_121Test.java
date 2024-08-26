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
 * @data 2024/8/23 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_121Test {

    @Test
    void maxProfit() {
        // [7,1,5,3,6,4]
        // prices = [3,3,5,0,0,3,1,4]
        // prices = [1,2,3,4,5]
        // prices = [7,6,4,3,1]
        int[] prices = {3,3,5,0,0,3,1,4};
//        int[] prices = {7,6,4,3,1};
        int result = new LeetCode_121().maxProfit(prices);
        System.out.println(result);
        int result_122 = new LeetCode_122().maxProfit(prices);
        System.out.println(result_122);
        int result_123 = new LeetCode_123().maxProfit(prices);
        System.out.println(result_123);
    }
}