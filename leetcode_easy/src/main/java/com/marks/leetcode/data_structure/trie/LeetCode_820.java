package com.marks.leetcode.data_structure.trie;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/4 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_820 {
    /**
     * @Description:
     * 单词数组 words 的 有效编码 由任意助记字符串 s 和下标数组 indices 组成，且满足：
     *
     * words.length == indices.length
     * 助记字符串 s 以 '#' 字符结尾
     * 对于每个下标 indices[i] ，s 的一个从 indices[i] 开始、到下一个 '#' 字符结束（但不包括 '#'）的 子字符串 恰好与 words[i] 相等
     * 给你一个单词数组 words ，返回成功对 words 进行编码的最小助记字符串 s 的长度 。
     *
     * tips:
     * 1 <= words.length <= 2000
     * 1 <= words[i].length <= 7
     * words[i] 仅由小写字母组成
     * @param words 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/4 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumLengthEncoding(String[] words) {
        int result;
        result = method_01(words);
        return result;
    }

    /**
     * @Description:
     * 字典树
     * 1. 降序排序
     * 2. 反转插入
     * 3. 只要有new TireString, 将 ans += word.length + 1
     *
     * AC: 18ms/47.02MB
     * @param words 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/4 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] words) {
        Arrays.sort(words, Comparator.comparing(String::length).reversed()); // 需要降序排序, 也就是先插入长的word
        Tire root = new Tire();
        int ans = 0;
        for (String word : words) {
            Tire node = root;
            boolean flag = false;
            for (int i = word.length() - 1; i >= 0; i--) {
                char ch = word.charAt(i);
                if (node.child[ch - 'a'] == null) {
                    flag = true;
                    node.child[ch - 'a'] = new Tire();
                }
                node = node.child[ch - 'a'];
            }
            if (flag) {
                ans += (word.length() + 1);
            }
        }
        return ans;
    }

    static class Tire {
        public Tire[] child;

        public Tire() {
            child = new Tire[26];
        }
    }
}
