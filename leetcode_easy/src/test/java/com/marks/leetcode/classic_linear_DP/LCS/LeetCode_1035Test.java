package com.marks.leetcode.classic_linear_DP.LCS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/12 14:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1035Test {

    @Test
    void maxUncrossedLines() {
        // nums1 = [2,5,1,2,5], nums2 = [10,5,2,1,5,2]
        int[] nums1 = new int[] {1,4,2};
        int[] nums2 = new int[] {1,2,4};
        int result = new LeetCode_1035().maxUncrossedLines(nums1, nums2);
        System.out.println(result);
    }
}