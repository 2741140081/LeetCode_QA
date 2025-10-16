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
 * @date 2025/10/16 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_396Test {

    @Test
    void maxRotateFunction() {
        int[] nums = {4,15,14,3,14,-8,12,-9,17,-1,15,1,10,19,-7,15,8,7,-8,11};
        LeetCode_396 leetCode_396 = new LeetCode_396();
        int result = leetCode_396.maxRotateFunction(nums);
        System.out.println(result);
        assertEquals(1511, result);
    }
}