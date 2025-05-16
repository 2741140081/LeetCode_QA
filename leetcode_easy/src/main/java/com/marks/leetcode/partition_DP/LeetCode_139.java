package com.marks.leetcode.partition_DP;

import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/18 17:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_139 {
    /**
     * @Description: [
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
     *
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     * tips:
     * 1 <= s.length <= 300
     * 1 <= wordDict.length <= 1000
     * 1 <= wordDict[i].length <= 20
     * s 和 wordDict[i] 仅由小写英文字母组成
     * wordDict 中的所有字符串 互不相同
     * ]
     * @param s
     * @param wordDict
     * @return boolean
     * @author marks
     * @CreateDate: 2024/9/18 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean result = false;
        result = method_01(s, wordDict);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
     * 输出: true
     * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。注意，你可以重复使用字典中的单词。
     * 我是否可以采用LeetCode_2369中的方式来解决这个题目
     * AC:9ms/43.40MB
     * ]
     * @param s
     * @param wordDict
     * @return boolean
     * @author marks
     * @CreateDate: 2024/9/18 17:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
