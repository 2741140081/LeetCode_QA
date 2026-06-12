package com.marks.leetcode.difference_array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3347Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/11 16:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3347Test {

    @Test
    void maxFrequency() {
        // 输入：nums = [1,4,5], k = 1, numOperations = 2
        int[] nums = {1,4,5};
        int k = 1;
        int numOperations = 2;
        LeetCode_3347 leetCode3347 = new LeetCode_3347();
        int result = leetCode3347.maxFrequency(nums, k, numOperations);
        System.out.println(result);
    }
}