package com.marks.leetcode.state_machine_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/9 17:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2771Test {

    @Test
    void maxNonDecreasingLength() {
        // 输入：nums1 = [1,3,2,1], nums2 = [2,2,3,4]
        // nums1 = [2,3,1], nums2 = [1,2,1]
        int[] nums1 = {5,16,15};
        int[] nums2 = {12,1,14};
        int result = new LeetCode_2771().maxNonDecreasingLength(nums1, nums2);
        System.out.println(result);
    }
}