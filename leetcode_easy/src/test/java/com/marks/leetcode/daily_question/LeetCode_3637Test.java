package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3637Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/3 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3637Test {

    @Test
    void isTrionic() {
        // 输入: nums = [1,3,5,4,2,6]
        //
        //输出: true
        int[] nums = {2,1,3};
        System.out.println(new LeetCode_3637().isTrionic(nums));
    }
}