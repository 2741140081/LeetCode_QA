package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3573Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/18 9:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3573Test {

    @Test
    void maximumProfit() {
        // 输入: prices = [12,16,19,19,8,1,19,13,9], k = 3
        int[] prices = {12,16,19,19,8,1,19,13,9};
        int k = 3;
        LeetCode_3573 leetCode_3573 = new LeetCode_3573();
        long result = leetCode_3573.maximumProfit(prices, k);
        System.out.println(result);
        // [4,16,8,18,4]
        int[] prices_1 = {4,16,8,18,4};
        long result_1 = leetCode_3573.maximumProfit(prices_1, 1);
        System.out.println(result_1);
    }
}