package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2731Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/28 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2731Test {

    @Test
    void sumDistance() {
        // 输入：nums = [-2,0,2], s = "RLL", d = 3
        int[] nums = {-2,0,2};
        String s = "RLL";
        int d = 3;
        LeetCode_2731 leetCode2731 = new LeetCode_2731();
        int result = leetCode2731.sumDistance(nums, s, d);
        System.out.println(result);
    }
}