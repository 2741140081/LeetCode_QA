package com.marks.leetcode.data_structure.prefix_sum;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/10 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2559 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的字符串数组 words 以及一个二维整数数组 queries 。
     *
     * 每个查询 queries[i] = [li, ri] 会要求我们统计在 words 中下标在 li 到 ri 范围内（包含 这两个值）并且以元音开头和结尾的字符串的数目。
     *
     * 返回一个整数数组，其中数组的第 i 个元素对应第 i 个查询的答案。
     *
     * 注意：元音字母是 'a'、'e'、'i'、'o' 和 'u' 。
     * @param words 
     * @param queries 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/10 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] vowelStrings(String[] words, int[][] queries) {
        int[] result;
        result = method_01(words, queries);
        return result;
    }

    /**
     * @Description:
     * AC:6ms/84.02MB
     * @param words
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/10 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String[] words, int[][] queries) {
        int n = words.length;
        int[] pre = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + (checkWord(words[i - 1]) ? 1 : 0);
        }
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            ans[i] = pre[right + 1] - pre[left];
        }
        return ans;
    }

    private boolean checkWord(String word) {
        return isVowel(word.charAt(0)) && isVowel(word.charAt(word.length() - 1));
    }

    private boolean isVowel(char ch) {
        return "aeiou".indexOf(ch) != -1;
    }
}
