package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/18 9:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1170 {
    /**
     * @Description: [
     * 定义一个函数 f(s)，统计 s  中（按字典序比较）最小字母的出现频次 ，其中 s 是一个非空字符串。
     *
     * 例如，若 s = "dcce"，那么 f(s) = 2，因为字典序最小字母是 "c"，它出现了 2 次。
     *
     * 现在，给你两个字符串数组待查表 queries 和词汇表 words 。对于每次查询 queries[i] ，
     * 需统计 words 中满足 f(queries[i]) < f(W) 的 词的数目 ，W 表示词汇表 words 中的每个词。
     *
     * 请你返回一个整数数组 answer 作为答案，其中每个 answer[i] 是第 i 次查询的结果。
     *
     * tips:
     * 1 <= queries.length <= 2000
     * 1 <= words.length <= 2000
     * 1 <= queries[i].length, words[i].length <= 10
     * queries[i][j]、words[i][j] 都由小写英文字母组成
     * ]
     * @param queries
     * @param words
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/18 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] result;
        result = method_01(queries, words);
        result = method_02(queries, words);
        return result;
    }

    /**
     * @Description: [官方题解: 后缀和
     *
     * AC:1ms/43.61MB
     *
     * ]
     * @param queries
     * @param words
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/18 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(String[] queries, String[] words) {
        int[] count = new int[12];
        for (String word : words) {
            count[f(word)]++;
        }
        // 后缀和
        for (int i = 9; i >= 1; i--) {
            count[i] += count[i + 1];
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String str = queries[i];
            res[i] = count[f(str) + 1]; // 满足f(W) > f(s)
        }
        return res;
    }

    /**
     * @Description: [功能描述]
     * @param str
     * @return int
     * @author marks
     * @CreateDate: 2024/11/18 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int f(String str) {
        int count = 0;
        char ch = 'z';
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < ch) {
                ch = c;
                count = 1;
            } else if (c == ch) {
                count++;
            }
        }
        return count;
    }

    /**
     * @Description: [
     * E1:
     * 输入：queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
     * 输出：[1,2]
     * 解释：第一个查询 f("bbb") < f("aaaa")，第二个查询 f("aaa") 和 f("aaaa") 都 > f("cc")。
     * [1, 2, 3, 4]
     * target = 3
     * left = 0, right = 3, mid = 1, pre[1] = 2 < target = 3
     * left = 2, right = 3, mid = 2, pre[2] = 3 == target = 3
     * left = 3, right = 3, mid = 3, pre[3] = 4 > target = 3
     * left = 3, right = 2, break;
     *
     * AC:4ms/43.87MB
     * ]
     * @param queries
     * @param words
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/18 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String[] queries, String[] words) {
        int n = queries.length;
        int m = words.length;
        int[] pre = new int[m];
        int[] res = new int[n];

        for (int i = 0; i < m; i++) {
            pre[i] = getArrayNum(words[i]);
        }
        Arrays.sort(pre);

        for (int i = 0; i < n; i++) {
            int target = getArrayNum(queries[i]);
            int left = 0;
            int right = m - 1;
            while (left <= right) {
                int mid = (right - left) / 2 + left;
                if (pre[mid] > target) {
                    right = mid - 1;
                } else if (pre[mid] <= target) {
                    left = mid + 1;
                }
            }
            res[i] = m - left;
        }
        return res;
    }

    private int getArrayNum(String str) {
        char[] array = str.toCharArray();
        int count = 0;
        Arrays.sort(array);
        for (char ch : array) {
            if (ch == array[0]) {
                count++;
            }
        }
        return count;
    }
}
