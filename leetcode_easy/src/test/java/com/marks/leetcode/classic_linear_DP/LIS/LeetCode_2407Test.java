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
 * @data 2024/8/23 15:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2407Test {

    @Test
    void lengthOfLIS() {
        //nums = [4,2,1,4,3,4,5,8,15], k = 3
        //nums = [1,4,7,15,5], k = 1
        int[] nums = {1,4,7,15,5};
        int k = 1;
        int result = new LeetCode_2407().lengthOfLIS(nums, k);
        System.out.println(result);
    }
}