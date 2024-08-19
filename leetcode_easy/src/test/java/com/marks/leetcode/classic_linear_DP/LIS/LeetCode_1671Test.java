package com.marks.leetcode.classic_linear_DP.LIS;

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
 * @data 2024/8/19 9:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1671Test {

    @Test
    void minimumMountainRemovals() {
        // nums = [2,1,1,5,6,2,3,1]
        int[] nums = {2,1,1,5,6,2,3,1};
        System.out.println(new LeetCode_1671().minimumMountainRemovals(nums));
    }
}