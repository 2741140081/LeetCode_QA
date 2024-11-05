package com.marks.leetcode.double_pointer.single_sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_16Test {

    @Test
    void threeSumClosest() {
        // nums = [-1,2,1,-4], target = 1
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        int result = new LeetCode_16().threeSumClosest(nums, target);
        System.out.println(result);
    }
}