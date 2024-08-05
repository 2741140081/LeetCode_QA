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
 * @data 2024/8/5 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3082Test {

    @Test
    void sumOfPower() {
        // nums = [1,2,3], k = 3
//        int[] nums = new int[]{1, 2, 3};
//        int k = 3;

        // nums = [1,1,2,1,2,4,2,1,5,5,5,4,5,4,5,1,2,5,5,1,5,5,4,4,5], k = 5
        int[] nums = new int[]{1,1,2,1,2,4,2,1,5,5,5,4,5,4,5,1,2,5,5,1,5,5,4,4,5};
        int k = 5;
        int result = new LeetCode_3082().sumOfPower(nums, k);
        System.out.println(result);

    }
}