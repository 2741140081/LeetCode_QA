package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2411Test {

    @Test
    void smallestSubarrays() {
//        int[] nums = {1,0,2,1,3};
        int[] nums = {1000000000};
        LeetCode_2411 leetCode_2411 = new LeetCode_2411();
        int[] result = leetCode_2411.smallestSubarrays(nums);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}