package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2560Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/24 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2560Test {

    @Test
    void minCapability() {
        // nums = [2,7,9,3,1], k = 2
        int[] nums = {2, 7, 9, 3, 1};
        int k = 2;
        LeetCode_2560 leetCode_2560 = new LeetCode_2560();
        System.out.println(leetCode_2560.minCapability(nums, k));
    }
}