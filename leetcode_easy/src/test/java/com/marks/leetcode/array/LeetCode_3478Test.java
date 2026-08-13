package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3478Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/15 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3478Test {

    @Test
    void findMaxSum() {
        // 输入：nums1 = [4,2,1,5,3], nums2 = [10,20,30,40,50], k = 2
        int[] nums1 = {4,2,1,5,3};
        int[] nums2 = {10,20,30,40,50};
        int k = 2;
        long[] result = new LeetCode_3478().findMaxSum(nums1, nums2, k);
        System.out.println(Arrays.toString(result));
    }
}