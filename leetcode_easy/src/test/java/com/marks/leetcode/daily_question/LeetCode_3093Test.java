package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3093Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/28 11:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3093Test {

    @Test
    void stringIndices() {
        // 输入：wordsContainer = ["abcd","bcd","xbcd"], wordsQuery = ["cd","bcd","xyz"]
        LeetCode_3093 leetCode_3093 = new LeetCode_3093();
        int[] ints = leetCode_3093.stringIndices(new String[]{"abcd", "bcd", "xbcd"}, new String[]{"cd", "bcd", "xyz"});
        for (int anInt : ints) {
            System.out.print(anInt + " ");
        }
    }
}