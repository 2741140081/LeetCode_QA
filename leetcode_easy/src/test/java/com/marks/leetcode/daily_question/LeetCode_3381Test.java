package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3381Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 9:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3381Test {

    @Test
    void maxSubarraySum() {
        //  nums = [-1,-2,-3,-4,-5], k = 4
        int[] nums = {-1, -2, -3, -4, -5};
        int k = 4;
        LeetCode_3381 leetCode_3381 = new LeetCode_3381();
        long result = leetCode_3381.maxSubarraySum(nums, k);
        System.out.println(result);
    }
}