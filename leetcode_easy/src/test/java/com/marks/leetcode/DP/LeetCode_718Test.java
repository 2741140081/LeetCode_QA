package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_718Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 17:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_718Test {

    @Test
    void findLength() {
        // 输入：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
        int[] nums1 = {1,2,3,2,1};
        int[] nums2 = {3,2,1,4,7};
        LeetCode_718 leetCode_718 = new LeetCode_718();
        int result = leetCode_718.findLength(nums1, nums2);
        System.out.println(result);
    }
}