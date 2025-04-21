package com.marks.leetcode.data_structure.trie;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/28 14:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_648 {
    /**
     * @Description:
     * 在英语中，我们有一个叫做 词根(root) 的概念，可以词根 后面 添加其他一些词组成另一个较长的单词——我们称这个词为 衍生词 (derivative)。
     * 例如，词根 help，跟随着 继承词 "ful"，可以形成新的单词 "helpful"。
     *
     * 现在，给定一个由许多 词根 组成的词典 dictionary 和一个用空格分隔单词形成的句子 sentence
     * 。你需要将句子中的所有 衍生词 用 词根 替换掉。如果 衍生词 有许多可以形成它的 词根，则用 最短 的 词根 替换它。
     *
     * 你需要输出替换之后的句子。
     * @param dictionary
     * @param sentence
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 14:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        String result;
        result = method_01(dictionary, sentence);
        result = method_02(dictionary, sentence);
        return result;
    }

    /**
     * @Description: 字典树
     * AC: 22ms/54.28MB
     * @param dictionary
     * @param sentence
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(List<String> dictionary, String sentence) {
        Trie trie = new Trie();
        for (String word : dictionary) {
            Trie curr = trie;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                curr.children.putIfAbsent(ch, new Trie());
                curr = curr.children.get(ch);
            }
            curr.children.put('#', new Trie());
        }

        String[] strs = sentence.split(" ");
        for (int i = 0; i < strs.length; i++) {
            strs[i] = findRoot(strs[i], trie);
        }
        return String.join(" ", strs);
    }

    private String findRoot(String str, Trie trie) {
        StringBuilder ans = new StringBuilder();
        Trie curr = trie;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (curr.children.containsKey('#')) {
                return ans.toString();
            }
            if (!curr.children.containsKey(ch)) {
                return str;
            }
            ans.append(ch);
            curr = curr.children.get(ch);
        }
        return ans.toString();
    }

    /**
     * @Description:
     * 暴力
     * AC: 52ms/52.48MB
     * @param dictionary
     * @param sentence
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/2/28 14:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(List<String> dictionary, String sentence) {
        StringBuilder ans = new StringBuilder();
        dictionary.sort(Comparator.comparingInt(String::length));
        String[] strs = sentence.split(" ");
        for (String str : strs) {
            boolean flag = true;
            for (String root : dictionary) {
                if (str.startsWith(root)) {
                    flag = false;
                    ans.append(root + " ");
                    break;
                } else if (root.length() > str.length()){
                    flag = false;
                    ans.append(str + " ");
                    break;
                }
            }
            if (flag) {
                ans.append(str + " ");
            }
        }
        return ans.toString().trim();
    }
}
class Trie {
    public Map<Character, Trie> children;

    public Trie() {
        this.children = new HashMap<>();
    }
}
