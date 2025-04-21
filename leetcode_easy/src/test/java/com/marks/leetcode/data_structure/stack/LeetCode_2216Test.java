package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/17 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2216Test {

    @Test
    void minDeletion() {
        int[] nums = new int[] {1,1,2,2,3,3};
        int result = new LeetCode_2216().minDeletion(nums);
        System.out.println(result);
    }
}