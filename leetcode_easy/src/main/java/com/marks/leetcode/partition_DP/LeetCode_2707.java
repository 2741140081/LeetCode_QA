package com.marks.leetcode.partition_DP;

import com.marks.utils.Trie;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/20 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2707 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的字符串 s 和一个单词字典 dictionary 。你需要将 s 分割成若干个 互不重叠 的子字符串，
     * 每个子字符串都在 dictionary 中出现过。s 中可能会有一些 额外的字符 不在任何子字符串中。
     *
     * 请你采取最优策略分割 s ，使剩下的字符 最少 。
     * ]
     * @param s
     * @param dictionary
     * @return int
     * @author marks
     * @CreateDate: 2024/9/20 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minExtraChar(String s, String[] dictionary) {
        int result = 0;
        result = method_01(s, dictionary);
        result = method_02(s, dictionary);
        return result;
    }
    /**
     * @Description: [
     * 使用字典树优化查询字符串是否存在数组中
     * AC:36ms/44.62MB
     * ]
     * @param s
     * @param dictionary
     * @return int
     * @author marks
     * @CreateDate: 2024/9/20 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s, String[] dictionary) {
        int n = s.length();
        Trie node = new Trie();
        for (String str : dictionary) {
            node.insert(str);
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int j = i - 1; j >= 0; j--) {
                if (node.search(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
        }
        return dp[n];
    }

    /**
     * @Description: [
     * 输入：s = "leetscode", dictionary = ["leet","code","leetcode"]
     * AC:201ms/44.36MB
     * ]
     * @param s
     * @param dictionary
     * @return int
     * @author marks
     * @CreateDate: 2024/9/20 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, String[] dictionary) {
        int n = s.length();
        List<String> list = new ArrayList<>();
        for (String str : dictionary) {
            list.add(str);
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int j = i - 1; j >= 0; j--) {
                if (list.contains(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
        }
        return dp[n];
    }
}
