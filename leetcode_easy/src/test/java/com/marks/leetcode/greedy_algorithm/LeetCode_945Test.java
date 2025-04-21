package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_945Test {

    @Test
    void minIncrementForUnique() {
        int[] nums = {3,2,1,2,1,7};
        int result = new LeetCode_945().minIncrementForUnique(nums);
        System.out.println(result);
    }
}