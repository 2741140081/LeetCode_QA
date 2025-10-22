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
 * @date 2025/10/20 17:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3316Test {

    @Test
    void maxRemovals() {
        LeetCode_3316 leetCode_3316 = new LeetCode_3316();
        int result = leetCode_3316.maxRemovals("abbaa", "aba", new int[]{0, 1, 2});
        System.out.println(result);
        assertEquals(1, result);
    }
}