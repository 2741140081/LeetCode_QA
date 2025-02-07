package com.marks.leetcode.data_structure.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3066Test {

    @Test
    void minOperations() {
        int[] nums = {97,73,5,78};
        int k = 98;
        int result = new LeetCode_3066().minOperations(nums, k);
        System.out.println(result);
    }
}