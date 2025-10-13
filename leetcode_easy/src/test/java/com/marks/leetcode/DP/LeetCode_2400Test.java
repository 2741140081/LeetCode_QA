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
 * @date 2025/10/13 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2400Test {

    @Test
    void numberOfWays() {
        LeetCode_2400 leetCode_2400 = new LeetCode_2400();
        int startPos = 1, endPos = 2, k = 3;
        int result = leetCode_2400.numberOfWays(startPos, endPos, k);
        System.out.println(result);
        assertEquals(3, result);
    }
}