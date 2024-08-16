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
 * @data 2024/8/14 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_300Test {

    @Test
    void lengthOfLIS() {
        // nums = [10,9,2,5,3,7,101,18]
        // nums = [1,3,6,7,9,4,10,5,6]
        // [3,5,6,2,5,4,19,5,6,7,12]
        int[] nums = new int[]{3,5,6,2,5,4,19,5,6,7,12};
        System.out.println(new LeetCode_300().lengthOfLIS(nums));
    }
}