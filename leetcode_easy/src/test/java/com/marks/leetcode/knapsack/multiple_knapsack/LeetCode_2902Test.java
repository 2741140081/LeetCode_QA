package com.marks.leetcode.knapsack.multiple_knapsack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2902Test {

    @Test
    void countSubMultisets() {
        // nums = [2,1,4,2,7], l = 1, r = 5
//        List<Integer> nums = Arrays.asList(2, 1, 4, 2, 7);
//        int l = 1;
//        int r = 5;

        // nums = [0,0,1,2,3], l = 2, r = 3
        List<Integer> nums = Arrays.asList(0, 0, 1, 2, 3);
        int l = 2;
        int r = 3;
        int result = new LeetCode_2902().countSubMultisets(nums, l, r);
        System.out.println(result);
    }
}