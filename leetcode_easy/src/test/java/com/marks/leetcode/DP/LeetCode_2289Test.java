package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2289Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 17:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2289Test {

    @Test
    void totalSteps() {
        // nums = [5,3,4,4,7,3,6,11,8,5,11]
        int[] nums = {7,14,4,14,13,2,6,13};
        LeetCode_2289 leetCode_2289 = new LeetCode_2289();
        int ans = leetCode_2289.totalSteps(nums);
        System.out.println(ans);
    }
}