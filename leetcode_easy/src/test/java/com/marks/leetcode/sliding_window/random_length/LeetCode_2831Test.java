package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/18 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2831Test {

    @Test
    void longestEqualSubarray() {
        // nums = [1,3,2,3,1,3], k = 3
        int[] arr = {1,3,2,3,1,3};

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            nums.add(arr[i]);
        }
        int k = 3;
        int result = new LeetCode_2831().longestEqualSubarray(nums, k);
        System.out.println(result);
    }
}