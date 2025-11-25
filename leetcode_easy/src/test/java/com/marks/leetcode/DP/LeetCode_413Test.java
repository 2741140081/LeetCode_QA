package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_413Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/24 11:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_413Test {

    @Test
    void numberOfArithmeticSlices() {
        LeetCode_413 leetCode_413 = new LeetCode_413();
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(leetCode_413.numberOfArithmeticSlices(nums));
    }
}