package com.marks.leetcode.hash_table;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_17_17 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_17_17 {

    /**
     * @Description:
     * 给定一个较长字符串big和一个包含较短字符串的数组smalls，
     * 设计一个方法，根据smalls中的每一个较短字符串，对big进行搜索。
     * 输出smalls中的字符串在big里出现的所有位置positions，其中positions[i]为smalls[i]出现的所有位置。
     * tips:
     * 0 <= len(big) <= 1000
     * 0 <= len(smalls[i]) <= 1000
     * smalls的总字符数不会超过 10^6。
     * 你可以认为smalls中没有重复字符串。
     * 所有出现的字符均为英文小写字母。
     * @param: big
     * @param: smalls
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/06/08 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] multiSearch(String big, String[] smalls) {
        int[][] result;
        result = method_01(big, smalls);
        return result;
    }

    /**
     * @Description:
     * 1. 使用字典树存储 smalls[] 中所有字符串
     * 2. 然后对 big 进行匹配, 从 i 开始进行字典树的查询, 字典树的结束位置需要 int finished = smallIdx; 初始值为 -1
     * AC: 30ms/85.71MB
     * @param: big
     * @param: smalls
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/06/08 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(String big, String[] smalls) {
        int n = smalls.length;
        TrieNode root = new TrieNode();
        for (int i = 0; i < n; i++) {
            root.insert(smalls[i], i);
        }
        List<Integer>[] ans = new List[n];
        for (int i = 0; i < n; i++) {
            ans[i] = new ArrayList<>();
        }
        int m = big.length();
        for (int i = 0; i < m; i++) {
            TrieNode node = root;
            int next = i;
            while (node != null && next < m) {
                if (node.finished) {
                    ans[node.index].add(i);
                }
                node = node.children[big.charAt(next) - 'a'];
                next++;
            }
            if (next == m && node != null) {
                if (node.finished) {
                    ans[node.index].add(i);
                }
            }
        }
        int[][] result = new int[n][];
        for (int i = 0; i < n; i++) {
            result[i] = ans[i].stream().mapToInt(Integer::intValue).toArray();
        }

        return result;
    }


    private class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public boolean finished = false;
        public int index = -1;

        public TrieNode() {
        }

        public void insert(String word, int idx) {
            if (word == null || word.isEmpty()) {
                return;
            }
            TrieNode node = this;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (node.children[ch - 'a'] == null) {
                    node.children[ch - 'a'] = new TrieNode();
                }
                node = node.children[ch - 'a'];
            }
            node.finished = true;
            node.index = idx;
        }
    }

}
