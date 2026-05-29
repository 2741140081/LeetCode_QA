package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3093 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/28 11:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3093 {

    /**
     * @Description:
     * 给你两个字符串数组 wordsContainer 和 wordsQuery 。
     * 对于每个 wordsQuery[i] ，你需要从 wordsContainer 中找到一个与 wordsQuery[i] 有 最长公共后缀 的字符串。
     * 如果 wordsContainer 中有两个或者更多字符串有最长公共后缀，那么答案为长度 最短 的。
     * 如果有超过两个字符串有 相同 最短长度，那么答案为它们在 wordsContainer 中出现 更早 的一个。
     *
     * 请你返回一个整数数组 ans ，其中 ans[i]是 wordsContainer中与 wordsQuery[i] 有 最长公共后缀 字符串的下标。
     *
     * tips:
     * 1 <= wordsContainer.length, wordsQuery.length <= 10^4
     * 1 <= wordsContainer[i].length <= 5 * 10^3
     * 1 <= wordsQuery[i].length <= 5 * 10^3
     * wordsContainer[i] 只包含小写英文字母。
     * wordsQuery[i] 只包含小写英文字母。
     * wordsContainer[i].length 的和至多为 5 * 10^5 。
     * wordsQuery[i].length 的和至多为 5 * 10^5 。
     * @param: wordsContainer
     * @param: wordsQuery
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/28 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        int[] result;
        result = method_01(wordsContainer, wordsQuery);
        return result;
    }

    /**
     * @Description:
     * 1. 这种找最长公共后缀, 使用字典树来存储, 即使用 wordsContainer 来构建字典树
     * 2. 假设 wordsQuery[i] 有 x 个公共后缀, x + 1 不存在,  x + 1 的字典树中找到后续最短并且index 值最小的索引
     * 3. 字典数中需要存储那些信息: 当前位于字典树数中 i 处, i 携带有 26 个子节点, 每个子节点需要携带剩余最少的的值 int remain; int index;
     * 4. 对于那些不存在的 word, 需要找到 wordsContainer 中最短的并且最小的索引
     * AC: 67ms/277.49MB
     * @param: wordsContainer
     * @param: wordsQuery
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/28 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String[] wordsContainer, String[] wordsQuery) {
        int n = wordsQuery.length;
        int[] ans = new int[n];
        int m = wordsContainer.length;
        // 创建字典树
        Node root = new Node();
        int minIndex = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            root.add(wordsContainer[i], i);
            if (min > wordsContainer[i].length()) {
                min = wordsContainer[i].length();
                minIndex = i;
            }
        }
        // 更新 root
        root.index = minIndex;
        root.remain = min;
        for (int i = 0; i < n; i++) {
            ans[i] = root.query(wordsQuery[i]);
        }

        return ans;
    }

    private class Node {
        int remain;
        int index;
        Node[] children;
        public Node() {
            this.remain = 0;
            this.index = -1;
            this.children = new Node[26];
        }

        public void add(String word, int idx) {
            Node node = this;
            int n = word.length();
            for (int i = n - 1; i >= 0; i--) {
                int index = word.charAt(i) - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new Node();
                    node.children[index].remain = i; // 剩余的字符数
                    node.children[index].index = idx; // 当前索引
                } else {
                    // 判断是否需要更新
                    if (node.children[index].remain > i) {
                        node.children[index].remain = i;
                        node.children[index].index = idx;
                    }
                }
                node = node.children[index];
            }
        }

        public int query(String word) {
            Node node = this;
            int n = word.length();
            for (int i = n - 1; i >= 0; i--) {
                int index = word.charAt(i) - 'a';
                if (node.children[index] == null) {
                    break;
                }
                node = node.children[index];
            }
            return node.index;
        }
    }

}
