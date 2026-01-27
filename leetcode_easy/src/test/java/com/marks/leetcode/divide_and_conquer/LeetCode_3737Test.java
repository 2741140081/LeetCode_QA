package com.marks.leetcode.divide_and_conquer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3737Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/27 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3737Test {

    @Test
    void countMajoritySubarrays() {
        int[] nums = new int[] {1,2,2,3};
        int target = 2;
        int result = new LeetCode_3737().countMajoritySubarrays(nums, target);
        System.out.println(result);
    }
}