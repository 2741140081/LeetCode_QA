package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/19 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1283Test {

    @Test
    void smallestDivisor() {
        // 输入：nums = [1,2,5,9], threshold = 6
        int[] nums = {1, 2, 5, 9};
        int threshold = 6;
        int result = new LeetCode_1283().smallestDivisor(nums, threshold);
    }
}