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
 * @date 2025/9/19 16:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2588Test {

    @Test
    void beautifulSubarrays() {
        int[] nums = {4, 3, 1, 2, 4};
        LeetCode_2588 leetCode_2588 = new LeetCode_2588();
        long result = leetCode_2588.beautifulSubarrays(nums);
        System.out.println(result);
        assertEquals(2, result);

    }
}