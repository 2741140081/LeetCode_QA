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
 * @data 2024/11/5 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_28Test {

    @Test
    void purchasePlans() {
        // nums = [2,2,1,9], target = 10
        int[] nums = {2, 2, 1, 9};
        int target = 10;
        int result = new LCP_28().purchasePlans(nums, target);
        System.out.println(result);
    }
}