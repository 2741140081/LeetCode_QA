package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 14:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_930Test {

    @Test
    void numSubarraysWithSum() {
        // nums = [1,0,1,0,1], goal = 2
        int[] nums = {0,0,0,0,0};
        int goal = 0;
        int result = new LeetCode_930().numSubarraysWithSum(nums, goal);
        System.out.println(result);
    }
}