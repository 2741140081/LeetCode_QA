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
 * @date 2025/9/22 15:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1734Test {

    @Test
    void decode() {
        LeetCode_1734 leetCode_1734 = new LeetCode_1734();
        int[] result = leetCode_1734.decode(new int[]{6,5,4,6});
        // 验证输出result = {2,4,1,5,3}
        assertArrayEquals(new int[]{2,4,1,5,3}, result);
    }
}