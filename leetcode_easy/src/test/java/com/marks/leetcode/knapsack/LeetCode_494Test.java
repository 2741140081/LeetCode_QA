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
 * @data 2024/7/31 18:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_494Test {

    @Test
    void findTargetSumWays() {
        // nums = [1,1,1,1,1], target = 3
        int[] nums = new int[]{1,1,1,1,1};
        int target = 3;
        int result = new LeetCode_494().findTargetSumWays(nums, target);
        System.out.println(result);
    }
}