package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/22 17:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1130Test {

    @Test
    void mctFromLeafValues() {
        int[] arr = {6, 2, 4};
        LeetCode_1130 leetCode_1130 = new LeetCode_1130();
        int result = leetCode_1130.mctFromLeafValues(arr);
        System.out.println(result);
        assertEquals(32, result);
    }
}