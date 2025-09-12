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
 * @date 2025/9/10 14:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1356Test {

    @Test
    void sortByBits() {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        LeetCode_1356 leetCode_1356 = new LeetCode_1356();
        int[] result = leetCode_1356.sortByBits(arr);
        System.out.println(result);
    }
}