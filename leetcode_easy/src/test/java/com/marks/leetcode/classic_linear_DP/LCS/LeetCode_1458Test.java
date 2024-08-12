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
 * @data 2024/8/12 14:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1458Test {

    @Test
    void maxDotProduct() {
        // nums1 = [2,1,-2,5], nums2 = [3,0,-6]
        int[] nums1 = new int[]{2, 1, -2, 5};
        int[] nums2 = new int[]{3, 0, -6};
        int result = new LeetCode_1458().maxDotProduct(nums1, nums2);
        System.out.println(result);

    }
}