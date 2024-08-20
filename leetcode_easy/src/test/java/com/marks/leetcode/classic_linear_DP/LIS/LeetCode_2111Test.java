package com.marks.leetcode.classic_linear_DP.LIS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/19 17:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2111Test {

    @Test
    void kIncreasing() {
        // arr = [4,1,5,2,6,2], k = 3
        int[] arr = {4,1,5,2,6,2};
        int k = 3;
        System.out.println(new LeetCode_2111().kIncreasing(arr, k));
    }
}