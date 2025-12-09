package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3583Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 10:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3583Test {

    @Test
    void specialTriplets() {
        // 8,4,2,8,4
        int[] nums = {8, 4, 2, 8, 4};
        LeetCode_3583 leetCode_3583 = new LeetCode_3583();
        int result = leetCode_3583.specialTriplets(nums);
        System.out.println(result);
    }
}