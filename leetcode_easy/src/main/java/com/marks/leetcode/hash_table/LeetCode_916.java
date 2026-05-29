package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_916 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_916 {

    /**
     * @Description:
     * 给你两个字符串数组 words1 和 words2。
     * 现在，如果 b 中的每个字母都出现在 a 中，包括重复出现的字母，那么称字符串 b 是字符串 a 的 子集 。
     * 例如，"wrr" 是 "warrior" 的子集，但不是 "world" 的子集。
     * 如果对 words2 中的每一个单词 b，b 都是 a 的子集，那么我们称 words1 中的单词 a 是 通用单词 。
     * 以数组形式返回 words1 中所有的 通用 单词。你可以按 任意顺序 返回答案。
     *
     * tips:
     * 1 <= words1.length, words2.length <= 10^4
     * 1 <= words1[i].length, words2[i].length <= 10
     * words1[i] 和 words2[i] 仅由小写英文字母组成
     * words1 中的所有字符串 互不相同
     * @param: words1
     * @param: words2
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/05/29 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> result;
        result = method_01(words1, words2);
        return result;
    }

    /**
     * @Description:
     * 1. 需要 words2 中所有的单词 b，b 都是 a 的子集
     * 2. 统计 words2 中单词 b 的每个字母出现的次数, 得到一个 int[] maxCount = new int[26]; maxCount 记录单词 b 中每个字母出现的最大次数.
     * 3. 对于 words1 中的单词 a, 统计 a 中每一个字母出现次数 int[] curr = new int[26];, 如果 每一个 curr[i] >= maxCount[i], 则
     * a 是 通用的单词, 否则不是
     * AC: 13ms/56.23MB
     * @param: words1
     * @param: words2
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/05/29 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] words1, String[] words2) {
        int[] maxCount = new int[26];
        // 遍历 words2[]
        for (String s : words2) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
                maxCount[c - 'a'] = Math.max(maxCount[c - 'a'], count[c - 'a']);
            }
        }
        List<String> ans = new ArrayList<>();
        for (String s : words1) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            boolean flag = true;
            for (int i = 0; i < 26; i++) {
                if (count[i] < maxCount[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans.add(s);
            }
        }

        return ans;
    }

}
