package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1498Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/28 16:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1498Test {

    @Test
    void numSubseq() {
        // nums = [2,3,3,4,6,7], target = 12
        int[] nums = new int[]{2,3,3,4,6,7};
        int target = 12;
        LeetCode_1498 leetCode_1498 = new LeetCode_1498();
        int result = leetCode_1498.numSubseq(nums, target);
        System.out.println(result);
    }
}