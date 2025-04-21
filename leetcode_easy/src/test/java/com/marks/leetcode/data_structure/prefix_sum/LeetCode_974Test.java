package com.marks.leetcode.data_structure.prefix_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 17:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_974Test {

    @Test
    void subarraysDivByK() {
        // nums =
        int[] nums = {4,5,0,-2,-3,1};
        int k = 5;
        int result = new LeetCode_974().subarraysDivByK(nums, k);
        System.out.println(result);
    }
}