package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3862Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/25 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3862Test {

    @Test
    void smallestBalancedIndex() {
        // 输入： nums = [2,8,2,2,5]
        int[] nums = {2,1,2};
        LeetCode_3862 leetCode_3862 = new LeetCode_3862();
        int result = leetCode_3862.smallestBalancedIndex(nums);
        System.out.println(result);
    }
}