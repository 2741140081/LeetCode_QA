package com.marks.leetcode.partition_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/24 10:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2547Test {

    @Test
    void minCost() {
        // nums = [1,2,1,2,1,3,3], k = 2
        int[] nums = {1,2,1,2,1,3,3};
        int k = 2;
        int result = new LeetCode_2547().minCost(nums, k);
        System.out.println(result);
    }
}