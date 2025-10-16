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
 * @date 2025/10/14 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_Interview_17_13Test {

    @Test
    void respace() {
        String[] dictionary = {"looked", "just", "like", "her", "brother"};
        String sentence = "jesslookedjustliketimherbrother";
        LeetCode_Interview_17_13 leetCode_Interview_17_13 = new LeetCode_Interview_17_13();
        int result = leetCode_Interview_17_13.respace(dictionary, sentence);
        System.out.println(result);
        assertEquals(7, result);
    }
}