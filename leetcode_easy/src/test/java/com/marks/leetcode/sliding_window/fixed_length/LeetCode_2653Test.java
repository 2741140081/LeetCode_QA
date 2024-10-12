package com.marks.leetcode.sliding_window.fixed_length;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2653Test {

    @Test
    void getSubarrayBeauty() {
        // 输入：nums = [1,-1,-3,-2,3], k = 3, x = 2
        int[] nums = {1,-1,-3,-2,3};
        int k = 3;
        int x = 2;
        int[] subarrayBeauty = new LeetCode_2653().getSubarrayBeauty(nums, k, x);
        System.out.println(Arrays.toString(subarrayBeauty));
    }
}