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
 * @data 2024/10/8 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_813Test {

    @Test
    void largestSumOfAverages() {
        int[] nums = {1,2,3,4,5,6,7};
        int k = 4;
        double result = new LeetCode_813().largestSumOfAverages(nums, k);
        System.out.println(result);
    }
}