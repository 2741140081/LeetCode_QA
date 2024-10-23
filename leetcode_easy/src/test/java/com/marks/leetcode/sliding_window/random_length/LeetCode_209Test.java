package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/23 17:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_209Test {

    @Test
    void minSubArrayLen() {
        int[] nums = {1,2,3,4,5};
        int target = 11;
        int result = new LeetCode_209().minSubArrayLen(target, nums);
    }
}