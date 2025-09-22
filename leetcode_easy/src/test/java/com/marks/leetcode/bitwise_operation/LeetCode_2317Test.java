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
 * @date 2025/9/19 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2317Test {

    @Test
    void maximumXOR() {
        int[] nums = {3,2,4,6};
        LeetCode_2317 leetCode_2317 = new LeetCode_2317();
        int result = leetCode_2317.maximumXOR(nums);
        System.out.println(result);
        assertEquals(result, 7);
    }
}