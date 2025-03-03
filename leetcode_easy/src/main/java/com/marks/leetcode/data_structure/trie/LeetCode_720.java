package com.marks.leetcode.data_structure.trie;

import com.marks.utils.Trie;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/28 16:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_720 {
    /**
     * @Description:
     * 给出一个字符串数组 words 组成的一本英语词典。
     * 返回 words 中最长的一个单词，该单词是由 words 词典中其他单词逐步添加一个字母组成。
     *
     * 若其中有多个可行的答案，则返回答案中字典序最小的单词。若无答案，则返回空字符串。
     *
     * 请注意，单词应该从左到右构建，每个额外的字符都添加到前一个单词的结尾。
     *
     * tips:
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 30
     * @param words
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String longestWord(String[] words) {
        String result;
        result = method_01(words);
        result = method_02(words);
        return result;
    }

    /**
     * @Description:
     * 字典树
     * AC: 12ms/44.33MB
     * @param words
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        String longest = "";
        for (String word : words) {
            if (trie.searchWord(word)) {
                if (word.length() > longest.length() || (word.length() == longest.length() && word.compareTo(longest) < 0)) {
                    longest = word;
                }
            }
        }
        return longest;
    }

    /**
     * @Description:
     * 1. 需要对words 进行排序, "ap" 是在 "a" 的基础之上添加一个单词"p" 构成
     *
     * 哈希集合
     * AC: 17ms/44.07MB
     * @param words
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String[] words) {
        Arrays.sort(words, (o1, o2) -> {
            if (o1.length() != o2.length()) {
                return Integer.compare(o1.length(), o2.length()); // 升序排序, 先将短的添加到字典中
            } else {
                return o2.compareTo(o1); // 字典序降序, 即字典序小的最后添加
            }
        });
        String longest = "";
        Set<String> candidates = new HashSet<>();
        candidates.add("");
        int n = words.length;
        for (int i = 0; i < n; i++) {
            String word = words[i];
            if (candidates.contains(word.substring(0, word.length() - 1))) {
                candidates.add(word);
                longest = word;
            }
        }
        return longest;
    }
}
