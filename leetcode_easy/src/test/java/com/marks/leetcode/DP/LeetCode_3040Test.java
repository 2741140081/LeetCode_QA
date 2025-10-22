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
 * @date 2025/10/17 11:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3040Test {

    @Test
    void maxOperations() {
        int[] nums = {2,2,1,2,1};
        LeetCode_3040 leetCode_3040 = new LeetCode_3040();
        int result = leetCode_3040.maxOperations(nums);
        System.out.println(result);
        assertEquals(2, result);
    }
}