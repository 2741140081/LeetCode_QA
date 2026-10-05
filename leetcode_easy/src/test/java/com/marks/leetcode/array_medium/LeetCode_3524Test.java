package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3524Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/21 14:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3524Test {

    @Test
    void resultArray() {
        //  nums = [1,2,3,4,5], k = 3
        int[] nums = {1,2,3,4,5};
        int k = 3;
        LeetCode_3524 leetCode_3524 = new LeetCode_3524();
        long[] result = leetCode_3524.resultArray(nums, k);
        System.out.println(Arrays.toString(result));
    }
}