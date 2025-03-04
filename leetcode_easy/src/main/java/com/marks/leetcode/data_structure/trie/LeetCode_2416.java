package com.marks.leetcode.data_structure.trie;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/4 17:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2416 {
    /**
     * @Description:
     * 给你一个长度为 n 的数组 words ，该数组由 非空 字符串组成。
     *
     * 定义字符串 term 的 分数 等于以 term 作为 前缀 的 words[i] 的数目。
     *
     * 例如，如果 words = ["a", "ab", "abc", "cab"] ，那么 "ab" 的分数是 2 ，因为 "ab" 是 "ab" 和 "abc" 的一个前缀。
     * 返回一个长度为 n 的数组 answer ，其中 answer[i] 是 words[i] 的每个非空前缀的分数 总和 。
     *
     * 注意：字符串视作它自身的一个前缀。
     *
     * tips:
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 1000
     * words[i] 由小写英文字母组成
     * @param words 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/4 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sumPrefixScores(String[] words) {
        int[] result;
        result = method_01(words);
        return result;
    }
    

    /**
     * @Description:
     * 字典树, 感觉很经典没特殊情况需要处理
     * AC: 545ms/199.95MB
     * @param words 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/4 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String[] words) {
        int n = words.length;
        int[] ans = new int[n];
        Tire root = new Tire();

        for (String word : words) {
            Tire node = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (node.child[ch - 'a'] == null) {
                    node.child[ch - 'a'] = new Tire();
                }
                node = node.child[ch - 'a'];
                node.count++;
            }
        }

        for (int i = 0; i < n; i++) {
            Tire node = root;
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                char ch = word.charAt(j);
                node = node.child[ch - 'a'];
                ans[i] += node.count;
            }
        }
        return ans;
    }

    static class Tire {
        public Tire[] child;
        public int count;

        public Tire() {
            child = new Tire[26];
            count = 0;
        }
    }
}
