package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3507Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3507Test {

    @Test
    void minimumPairRemoval() {
        // nums = [5,2,3,1]
        int[] nums = {5, 2, 3, 1};
        int result = new LeetCode_3507().minimumPairRemoval(nums);
        System.out.println(result);
    }
}