package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/25 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2871Test {

    @Test
    void maxSubarrays() {
        int[] nums = {22,21,29,22};
        LeetCode_2871 leetCode_2871 = new LeetCode_2871();
        int result = leetCode_2871.maxSubarrays(nums);
        System.out.println(result);
        assertEquals(1, result);
    }
}