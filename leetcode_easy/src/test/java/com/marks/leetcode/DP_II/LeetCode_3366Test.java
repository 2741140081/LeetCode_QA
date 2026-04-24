package com.marks.leetcode.DP_II;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3366Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/22 16:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3366Test {

    @Test
    void minArraySum() {
        // 输入： nums = [2,4,3], k = 3, op1 = 2, op2 = 1
        int[] nums = {2,4,3};
        int k = 3;
        int op1 = 2;
        int op2 = 1;
        LeetCode_3366 leetCode_3366 = new LeetCode_3366();
        int i = leetCode_3366.minArraySum(nums, k, op1, op2);
        System.out.println(i);
    }
}