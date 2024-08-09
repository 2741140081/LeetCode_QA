package com.marks.leetcode.classic_linear_DP.LCS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1143Test {

    @Test
    void longestCommonSubsequence() {
        // text1 = "abcde", text2 = "ace"
//        String text1 = "abcde";
//        String text2 = "ace";

        // text1 = "abcba", text2 = "abcbcba"
        String text1 = "abcba";
        String text2 = "abcbcba";
        int result = new LeetCode_1143().longestCommonSubsequence(text1, text2);
        System.out.println(result);
    }
}