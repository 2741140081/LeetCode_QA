package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3224Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/9 15:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3224Test {

    @Test
    void minChanges() {
        // int[] nums = {1,1,1,1,0,0,0,5,4,3,19,17,16,15,15,15,19,19,19,19}, int k = 20
        int[] nums = {1,1,1,1,0,0,0,5,4,3,19,17,16,15,15,15,19,19,19,19};
        int k = 20;
        LeetCode_3224 leetCode3224 = new LeetCode_3224();
        int result = leetCode3224.minChanges(nums, k);
        System.out.println(result);
    }
}