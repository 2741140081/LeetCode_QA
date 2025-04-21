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
 * @date 2025/1/14 15:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1477Test {

    @Test
    void minSumOfLengths() {
        // arr = [3,2,2,4,3], target = 3
        int[] arr = new int[] {3, 2, 2, 4, 3};
        int target = 3;
        int result = new LeetCode_1477().minSumOfLengths(arr, target);
        System.out.println(result);
    }
}