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
 * @data 2024/11/1 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2302Test {

    @Test
    void countSubarrays() {
        int[] nums = {2,1,4,3,5};
        long k = 10;
        long result = new LeetCode_2302().countSubarrays(nums, k);
        System.out.println(result);
    }
}