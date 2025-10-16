package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 18:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3202Test {

    @Test
    void maximumLength() {
        int[] nums = {1,2,3,10,2};
        int k = 6;
        LeetCode_3202 leetCode_3202 = new LeetCode_3202();
        int result = leetCode_3202.maximumLength(nums, k);
        System.out.println(result);
        assertEquals(3, result);
    }
}