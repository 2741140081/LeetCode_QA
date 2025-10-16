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
 * @date 2025/10/14 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1621Test {

    @Test
    void numberOfSets() {
        LeetCode_1621 leetCode_1621 = new LeetCode_1621();
        int result = leetCode_1621.numberOfSets(4, 2);
        System.out.println(result);
        assertEquals(5, result);
    }
}