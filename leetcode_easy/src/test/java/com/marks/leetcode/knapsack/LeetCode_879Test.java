package com.marks.leetcode.knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/5 14:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_879Test {

    @Test
    void profitableSchemes() {
        // E1:n = 10, minProfit = 5, group = [2,3,5], profit = [6,7,8]
        int n = 20;
        int minProfit = 15;
        int[] group = new int[]{2, 3, 5, 7, 8};
        int[] profit = new int[]{6, 7, 8, 10, 12};



        int result = new LeetCode_879().profitableSchemes(n, minProfit, group, profit);
        System.out.println(result);
    }
}