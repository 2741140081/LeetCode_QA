package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1696Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/2 10:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1696Test {

    @Test
    void maxResult() {
        // 输入：nums = [1,-1,-2,4,-7,3], k = 2
        //输出：7
        int[] nums = {1, -1, -2, 4, -7, 3};
        int k = 2;
        LeetCode_1696 leetCode_1696 = new LeetCode_1696();
        System.out.println(leetCode_1696.maxResult(nums, k));
    }
}