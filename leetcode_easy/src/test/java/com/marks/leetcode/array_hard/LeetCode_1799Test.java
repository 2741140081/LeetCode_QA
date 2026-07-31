package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1799Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/31 17:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1799Test {

    @Test
    void maxScore() {
        // [5, 5]
        int[] nums = {5, 5};
        LeetCode_1799 leetCode1799 = new LeetCode_1799();
        int result = leetCode1799.maxScore(nums);
        System.out.println(result);
    }
}