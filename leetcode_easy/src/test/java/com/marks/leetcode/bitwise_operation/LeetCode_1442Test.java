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
 * @date 2025/9/18 14:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1442Test {

    @Test
    void countTriplets() {
        int[] arr = {2, 3, 1, 6, 7};
        LeetCode_1442 leetCode_1442 = new LeetCode_1442();
        int result = leetCode_1442.countTriplets(arr);
        System.out.println(result);
        assertEquals(4, result);
    }
}