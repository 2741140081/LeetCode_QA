package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1938 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/4 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1938 {

    /**
     * @Description:
     * 给你一棵 n 个节点的有根树，节点编号从 0 到 n - 1 。
     * 每个节点的编号表示这个节点的 独一无二的基因值 （也就是说节点 x 的基因值为 x）。
     * 两个基因值的 基因差 是两者的 异或和 。
     * 给你整数数组 parents ，其中 parents[i] 是节点 i 的父节点。
     * 如果节点 x 是树的 根 ，那么 parents[x] == -1 。
     * 给你查询数组 queries ，其中 queries[i] = [nodei, vali] 。
     * 对于查询 i ，请你找到 vali 和 pi 的 最大基因差 ，其中 pi 是节点 nodei 到根之间的任意节点（包含 nodei 和根节点）。
     * 更正式的，你想要最大化 vali XOR pi 。
     * 请你返回数组 ans ，其中 ans[i] 是第 i 个查询的答案。
     *
     * tips:
     * 2 <= parents.length <= 10^5
     * 对于每个 不是 根节点的 i ，有 0 <= parents[i] <= parents.length - 1 。
     * parents[root] == -1
     * 1 <= queries.length <= 3 * 10^4
     * 0 <= nodei <= parents.length - 1
     * 0 <= vali <= 2 * 10^5
     * @param: parents
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/04 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maxGeneticDifference(int[] parents, int[][] queries) {
        int[] result;
        result = method_01(parents, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 预处理 p, 得到 nodeI 到 root 之间的所有节点集合, 并且将这个集合将其转成 01 字典树,
     * 由于最大位数 20 位, 2 * 10^5,
     * AC: 219ms/223.27MB
     * @param: parents
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/04 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private final int MAX_BIT = 20;

    private int[] method_01(int[] parents, int[][] queries) {
        int n = parents.length;
        int m = queries.length;
        int[] ans = new int[m];

        // 1. 构建树的邻接表
        List<List<Integer>> tree = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }
        int rootIndex = -1;
        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                rootIndex = i;
            } else {
                tree.get(parents[i]).add(i);
            }
        }

        // 2. 将查询按节点分组，queriesMap[i] 存储所有关于节点 i 的查询 {queryIndex, val}
        Map<Integer, List<int[]>> queriesMap = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int node = queries[i][0];
            int val = queries[i][1];
            queriesMap.computeIfAbsent(node, k -> new ArrayList<>()).add(new int[]{i, val});
        }

        // 3. DFS 遍历树，动态维护 Trie
        dfs(rootIndex, tree, queriesMap, ans);

        return ans;
    }

    private void dfs(int node, List<List<Integer>> tree, Map<Integer, List<int[]>> queriesMap, int[] ans) {
        // 1. 进入节点：将当前节点值插入 Trie
        insert(node);

        // 2. 处理当前节点的所有查询
        if (queriesMap.containsKey(node)) {
            for (int[] q : queriesMap.get(node)) {
                int queryIndex = q[0];
                int val = q[1];
                ans[queryIndex] = queryMaxXor(val);
            }
        }

        // 3. 递归处理子节点
        for (int child : tree.get(node)) {
            dfs(child, tree, queriesMap, ans);
        }

        // 4. 离开节点（回溯）：从 Trie 中移除当前节点值
        remove(node);
    }

    // 定义 Trie 节点
    static class TrieNode {
        TrieNode[] child = new TrieNode[2];
        int count = 0; // 记录经过该节点的数字数量，用于删除操作
    }

    private TrieNode root = new TrieNode();

    // 插入数字
    private void insert(int num) {
        TrieNode node = root;
        for (int i = MAX_BIT; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (node.child[bit] == null) {
                node.child[bit] = new TrieNode();
            }
            node = node.child[bit];
            node.count++;
        }
    }

    // 删除数字
    private void remove(int num) {
        TrieNode node = root;
        for (int i = MAX_BIT; i >= 0; i--) {
            int bit = (num >> i) & 1;
            node = node.child[bit];
            node.count--;
        }
    }

    // 查询最大异或
    private int queryMaxXor(int val) {
        TrieNode node = root;
        int ans = 0;
        for (int i = MAX_BIT; i >= 0; i--) {
            int bit = (val >> i) & 1;
            int target = 1 - bit;
            // 优先走相反的位，且该分支必须存在（count > 0）
            if (node.child[target] != null && node.child[target].count > 0) {
                ans |= (1 << i);
                node = node.child[target];
            } else {
                node = node.child[bit];
            }
        }
        return ans;
    }

}
