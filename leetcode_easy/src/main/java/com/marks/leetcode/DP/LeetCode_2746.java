package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 17:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2746 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的数组 words ，它包含 n 个字符串。
     *
     * 定义 连接 操作 join(x, y) 表示将字符串 x 和 y 连在一起，得到 xy 。如果 x 的最后一个字符与 y 的第一个字符相等，连接后两个字符中的一个会被 删除 。
     *
     * 比方说 join("ab", "ba") = "aba" ， join("ab", "cde") = "abcde" 。
     *
     * 你需要执行 n - 1 次 连接 操作。令 str0 = words[0] ，从 i = 1 直到 i = n - 1 ，对于第 i 个操作，你可以执行以下操作之一：
     *
     * 令 stri = join(stri - 1, words[i])
     * 令 stri = join(words[i], stri - 1)
     * 你的任务是使 strn - 1 的长度 最小 。
     *
     * 请你返回一个整数，表示 strn - 1 的最小长度。
     *
     * tips:
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 50
     * words[i] 中只包含小写英文字母。
     * @param words
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 17:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimizeConcatenatedLength(String[] words) {
        int result;
        result = method_01(words);
        return result;
    }

    
    /**
     * @Description:
     * int currWordStart = currWord.charAt(0) - 'a';
     * int currWordEnd = currWord.charAt(currWord.length() - 1) - 'a';
     * dp[i][currWordStart][k] = Math.min(dp[i - 1][j][k] + currWord.length() - (currWordEnd == j ? 1 : 0), dp[i][currWordStart][k]);
     * dp[i][j][currWordEnd] = Math.min(dp[i - 1][j][k] + currWord.length() - (currWordStart == k ? 1 : 0), dp[i][j][currWordEnd]);
     *
     * AC: 133ms/46.71MB
     * @param words
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 17:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] words) {
        int n = words.length;
        int[][][] dp = new int[n][26][26];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    dp[i][j][k] = 50001;
                }
            }
        }
        dp[0][words[0].charAt(0) - 'a'][words[0].charAt(words[0].length() - 1) - 'a'] = words[0].length();
        for (int i = 1; i < n; i++) {
            String currWord = words[i];
            for (int j = 0; j < 26; j++) {
                for (int k = 0; k < 26; k++) {
                    // word + str_i
                    int currWordStart = currWord.charAt(0) - 'a';
                    int currWordEnd = currWord.charAt(currWord.length() - 1) - 'a';
                    dp[i][currWordStart][k] = Math.min(dp[i - 1][j][k] + currWord.length() - (currWordEnd == j ? 1 : 0),
                            dp[i][currWordStart][k]);
                    // str_i + word
                    dp[i][j][currWordEnd] = Math.min(dp[i - 1][j][k] + currWord.length() - (currWordStart == k ? 1 : 0), dp[i][j][currWordEnd]);
                }
            }
        }
        int result = 50001;
        for (int j = 0; j < 26; j++) {
            for (int k = 0; k < 26; k++) {
                result = Math.min(result, dp[n - 1][j][k]);
            }
        }
        
        return result;
    }

}
