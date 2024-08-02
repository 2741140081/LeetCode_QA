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
 * @data 2024/8/2 16:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1774Test {

    @Test
    void closestCost() {
        // baseCosts = [1,7], toppingCosts = [3,4], target = 10
//        int[] baseCosts = new int[]{1, 7};
//        int[] toppingCosts = new int[]{3, 4};
//        int target = 10;

        // baseCosts = [2,3] toppingCosts = [4,5,100] target =18
        int[] baseCosts = new int[]{2,3};
        int[] toppingCosts = new int[]{4,5,100};
        int target = 18;
        int result = new LeetCode_1774().closestCost(baseCosts, toppingCosts, target);
        System.out.println(result);
    }
}