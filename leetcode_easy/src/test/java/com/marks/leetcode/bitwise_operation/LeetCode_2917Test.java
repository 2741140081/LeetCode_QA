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
 * @date 2025/9/11 17:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2917Test {

    @Test
    void findKOr() {
        int[] nums = {7,12,9,8,9,15};
        int k = 4;
        LeetCode_2917 leetCode_2917 = new LeetCode_2917();
        int result = leetCode_2917.findKOr(nums, k);
        System.out.println(result);
        assertEquals(9, result);
    }
}