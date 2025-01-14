package com.marks.leetcode.data_structure.prefix_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 15:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3026Test {

    @Test
    void maximumSubarraySum() {
        int[] nums = {1, 2, 3, 4, 5, 6};
        int k = 1;
        long result = new LeetCode_3026().maximumSubarraySum(nums, k);
        System.out.println(result);
    }
}