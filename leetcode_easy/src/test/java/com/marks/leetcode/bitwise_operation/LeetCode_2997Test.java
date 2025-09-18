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
 * @date 2025/9/18 14:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2997Test {

    @Test
    void minOperations() {
        int[] nums = {2,1,3,4};
        int k = 1;
        LeetCode_2997 leetCode_2997 = new LeetCode_2997();
        int result = leetCode_2997.minOperations(nums, k);
        System.out.println(result);
        assertTrue(result == 2);
    }
}