package com.marks.leetcode.monotonic_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 10:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_496Test {

    @Test
    void nextGreaterElement() {
        // nums1 = [4,1,2], nums2 = [1,3,4,2].
        int[] nums1 = {4,1,2};
        int[] nums2 = {1,3,4,2};
        int[] result = new LeetCode_496().nextGreaterElement(nums1, nums2);
    }
}