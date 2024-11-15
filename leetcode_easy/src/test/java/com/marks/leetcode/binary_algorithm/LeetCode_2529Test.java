package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/15 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2529Test {

    @Test
    void maximumCount() {
        // nums = [-2,-1,-1,1,1,2,3]
        int[] nums = {-2,-1,-1,0,0,1,1,2,3};
        int result = new LeetCode_2529().maximumCount(nums);
        System.out.println(result);
    }
}