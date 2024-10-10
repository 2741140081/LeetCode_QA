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
 * @data 2024/10/9 16:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2090Test {

    @Test
    void getAverages() {
        int[] nums = {7,4,3,9,1,8,5,2,6};
        int k = 3;
        int[] result = new LeetCode_2090().getAverages(nums, k);
        System.out.println(Arrays.toString(result));
    }
}