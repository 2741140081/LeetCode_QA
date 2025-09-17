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
 * @date 2025/9/12 10:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2657Test {

    @Test
    void findThePrefixCommonArray() {
        int[] A = {1,3,2,4};
        int[] B = {3,1,2,4};
        LeetCode_2657 leetCode_2657 = new LeetCode_2657();
        int[] result = leetCode_2657.findThePrefixCommonArray(A, B);
        System.out.println(result);
        assertArrayEquals(new int[]{0,2,3,4}, result);
    }
}