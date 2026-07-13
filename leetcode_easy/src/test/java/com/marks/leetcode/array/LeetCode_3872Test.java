package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3872Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/13 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3872Test {

    @Test
    void longestArithmetic() {
        // {1, 1, 1, 1}
        int[] nums = {1, 1, 3, 4};
        int result = new LeetCode_3872().longestArithmetic(nums);
        System.out.println(result);
    }
}