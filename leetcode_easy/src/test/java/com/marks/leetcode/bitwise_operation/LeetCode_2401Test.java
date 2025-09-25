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
 * @date 2025/9/25 11:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2401Test {

    @Test
    void longestNiceSubarray() {
        int[] nums = {84139415,693324769,614626365,497710833,615598711,264,65552,50331652,1,1048576,16384,544,270532608,151813349,221976871,678178917,845710321,751376227,331656525,739558112,267703680};
        LeetCode_2401 leetCode_2401 = new LeetCode_2401();
        int result = leetCode_2401.longestNiceSubarray(nums);
        System.out.println(result);
        assertEquals(8, result);
    }
}