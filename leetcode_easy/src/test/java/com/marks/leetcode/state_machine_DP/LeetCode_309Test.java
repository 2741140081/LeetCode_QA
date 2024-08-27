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
 * @data 2024/8/26 17:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_309Test {

    @Test
    void maxProfit() {
        int[] prices = {1,2,3,0,2};
//        int[] prices = {1,2,4};
        int result = new LeetCode_309().maxProfit(prices);
        System.out.println(result);
    }
}