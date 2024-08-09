package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称: LCS </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_583 {
    public int minDistance(String word1, String word2) {
        int result = 0;
        int wordCommon = new LeetCode_1143().longestCommonSubsequence(word1, word2);
        result = word1.length() + word2.length() - wordCommon * 2;
        return result;
    }
}
