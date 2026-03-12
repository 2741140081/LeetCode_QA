package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2439Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/11 17:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2439Test {

    @Test
    void minimizeArrayValue() {
        // nums = [3,7,1,6]
        int[] nums = {3,7,1,6};
        int result = new LeetCode_2439().minimizeArrayValue(nums);
        System.out.println(result);
    }
}