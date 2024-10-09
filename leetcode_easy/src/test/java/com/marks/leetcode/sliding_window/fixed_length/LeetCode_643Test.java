package com.marks.leetcode.sliding_window.fixed_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_643Test {

    @Test
    void findMaxAverage() {
        // [1,12,-5,-6,50,3]
        int[] nums = {1,12,-5,-6,50,3};
        int k = 4;
        double res = new LeetCode_643().findMaxAverage(nums, k);
        System.out.println(res);
    }
}