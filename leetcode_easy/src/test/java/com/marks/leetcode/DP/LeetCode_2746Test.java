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
 * @date 2025/10/13 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2746Test {

    @Test
    void minimizeConcatenatedLength() {
        String[] words = {"aa","ab","bc"};
        LeetCode_2746 leetCode_2746 = new LeetCode_2746();
        int result = leetCode_2746.minimizeConcatenatedLength(words);
        System.out.println(result);
        assertEquals(4, result);
    }
}