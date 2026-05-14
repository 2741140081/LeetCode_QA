package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_581Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 10:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_581Test {

    @Test
    void findUnsortedSubarray() {
        // nums = [2,6,4,8,10,9,15]
        int[] nums = {2,6,4,8,10,9,15};
        int result = new LeetCode_581().findUnsortedSubarray(nums);
        System.out.println(result);
    }
}