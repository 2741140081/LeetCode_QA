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
 * @data 2024/10/14 16:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2779Test {

    @Test
    void maximumBeauty() {
        // nums = [4,6,1,2], k = 2
        int[] nums = {4,6,1,2};
        int k = 2;
        int result = new LeetCode_2779().maximumBeauty(nums, k);
        System.out.println(result);
    }
}