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
 * @date 2025/9/26 17:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3171Test {

    @Test
    void minimumDifference() {
        int[] nums = {6};
        int k = 2;
        LeetCode_3171 leetCode_3171 = new LeetCode_3171();
        int result = leetCode_3171.minimumDifference(nums, k);
        assertEquals(4, result);
    }
}