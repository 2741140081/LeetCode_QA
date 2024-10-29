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
        // 18,3,11,19,7,16,6,7,3,6,18,9,9,1,14,17,15,14,12,10  target = 7
//        int[] nums = {18,3,11,19,7,16,6,7,3,6,18,9,9,1,14,17,15,14,12,10};
        // 5,5,4,1,2,2,2,3,2,4,2,5 target = 56
        int[] nums = {5,5,4,1,2,2,2,3,2,4,2,5};
        int target = 56;
        int result = new LeetCode_2875().minSizeSubarray(nums, target);
        System.out.println(result);
    }
}