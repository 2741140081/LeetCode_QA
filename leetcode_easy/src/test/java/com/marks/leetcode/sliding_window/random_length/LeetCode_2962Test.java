package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/29 17:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2962Test {

    @Test
    void countSubarrays() {
        int[] nums = {1,3,2,3,3};
        int k = 2;
        long result = new LeetCode_2962().countSubarrays(nums, k);
        System.out.println(result);
    }
}