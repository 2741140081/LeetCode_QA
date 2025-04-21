package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3457Test {

    @Test
    void maxWeight() {
        // pizzas = [5,2,2,4,3,3,1,3,2,5,4,2]
        int[] pizzas = {5,5,4,4,3,1,2,4,2,4,1,2,2,3,2,2,2,4,5,2};
//        int[] pizzas = {1,2,3,4,5,6,7,8};
        long result = new LeetCode_3457().maxWeight(pizzas);
        System.out.println(result);
    }
}