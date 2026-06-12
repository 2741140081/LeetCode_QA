package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3654Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 15:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3654Test {

    @Test
    void minArraySum() {
        // 输入： nums = [3,1,4,1,5], k = 3
        int[] nums = {3,1,4,1,5};
        int k = 3;
        LeetCode_3654 leetCode_3654 = new LeetCode_3654();
        System.out.println(leetCode_3654.minArraySum(nums, k));
    }
}