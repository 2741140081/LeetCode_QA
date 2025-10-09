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
 * @date 2025/10/9 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1521Test {

    @Test
    void closestToTarget() {
        int test = 1000000;
        String str = Integer.toBinaryString(test);
        System.out.println(str.length());
        int[] arr = {9, 12, 3, 7, 15};
        int target = 5;
        LeetCode_1521 leetCode_1521 = new LeetCode_1521();
        int result = leetCode_1521.closestToTarget(arr, target);
        System.out.println(result);
        assertEquals(2, result);
    }
}