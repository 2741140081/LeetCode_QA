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
 * @data 2024/10/25 17:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2875Test {

    @Test
    void minSizeSubarray() {
        int[] nums = {1,1,1,2,3};
        int target = 20;
        int result = new LeetCode_2875().minSizeSubarray(nums, target);
        System.out.println(result);
    }
}