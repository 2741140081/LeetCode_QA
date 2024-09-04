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
 * @data 2024/9/4 11:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1262Test {

    @Test
    void maxSumDivThree() {
        // nums = [3,6,5,1,8]
        int[] nums = {3,6,5,1,8};
        int result = new LeetCode_1262().maxSumDivThree(nums);
        System.out.println(result);
    }
}