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
 * @data 2024/8/16 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_673Test {

    @Test
    void findNumberOfLIS() {
//        int[] nums = {1,2,4,3,5,4,7,2};
        int[] nums = {2,2,2,2,2};
        int res = new LeetCode_673().findNumberOfLIS(nums);
        System.out.println(res);
    }
}