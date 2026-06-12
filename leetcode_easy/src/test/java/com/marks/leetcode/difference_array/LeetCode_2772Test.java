package com.marks.leetcode.difference_array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2772Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/11 14:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2772Test {

    @Test
    void checkArray() {
        // nums = [2,2,3,1,1,0], k = 3
        int[] nums = {60,72,87,89,63,52,64,62,31,37,57,83,98,94,92,77,94,91,87,100,91,91,50,26};
        int k = 4;
        LeetCode_2772 leetCode2772 = new LeetCode_2772();
        boolean result = leetCode2772.checkArray(nums, k);
        System.out.println(result);
    }
}