package com.marks.leetcode.state_machine_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/4 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2786Test {

    @Test
    void maxScore() {
        // 输入：nums = [2,3,6,1,9,2], x = 5
        int[] nums = {2,3,6,1,9,2};
        int x = 5;
        long result = new LeetCode_2786().maxScore(nums, x);
        System.out.println(result);
    }
}