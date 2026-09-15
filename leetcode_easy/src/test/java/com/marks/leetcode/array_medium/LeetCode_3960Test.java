package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3960Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/15 11:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3960Test {

    @Test
    void getLength() {
        // 输入： nums = [1,2,2,1,2,3,3,3]
        int[] nums = {1,2,2,1,2,3,3,3};
        int result = new LeetCode_3960().getLength(nums);
        System.out.println(result);
    }
}