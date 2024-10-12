package com.marks.leetcode.sliding_window.fixed_length;

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
 * @data 2024/10/12 10:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2841Test {

    @Test
    void maxSum() {
        // nums = [2,6,7,3,1,7], m = 3, k = 4
        // nums = [1,2,1,2,1,2,1], m = 3, k = 3
        Integer[] arr = {1,2,1,2,1,2,1};
        List<Integer> nums = new ArrayList<>(Arrays.asList(arr));
        int m = 3;
        int k = 4;
        long result = new LeetCode_2841().maxSum(nums, m, k);
        System.out.println(result);

    }
}