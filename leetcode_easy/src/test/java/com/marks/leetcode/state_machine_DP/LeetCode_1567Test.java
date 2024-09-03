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
 * @data 2024/9/3 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1567Test {

    @Test
    void getMaxLen() {
        //nums = [-1,-2,-3,0,1]
        // [1,2,3,5,-6,4,0,10]
        int[] nums = {1,2,3,5,-6,-4,-5,4,0,10};
        int reuslt = new LeetCode_1567().getMaxLen(nums);
        System.out.println(reuslt);
    }
}