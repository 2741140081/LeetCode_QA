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
 * @data 2024/8/26 15:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_188Test {

    @Test
    void maxProfit() {
        // k = 2, prices = [3,2,6,5,0,3]
        int k = 2;
        int[] prices = {3,2,6,5,0,3};
        int result = new LeetCode_188().maxProfit(k, prices);
        System.out.println(result);
    }
}