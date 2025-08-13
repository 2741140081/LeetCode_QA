package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/13 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2246 {

    /**
     * @Description:
     * 给你一棵 树（即一个连通、无向、无环图），根节点是节点 0 ，这棵树由编号从 0 到 n - 1 的 n 个节点组成。
     * 用下标从 0 开始、长度为 n 的数组 parent 来表示这棵树，其中 parent[i] 是节点 i 的父节点，由于节点 0 是根节点，所以 parent[0] == -1 。
     *
     * 另给你一个字符串 s ，长度也是 n ，其中 s[i] 表示分配给节点 i 的字符。
     *
     * 请你找出路径上任意一对相邻节点都没有分配到相同字符的 最长路径 ，并返回该路径的长度。
     *
     * tips:
     * n == parent.length == s.length
     * 1 <= n <= 10^5
     * 对所有 i >= 1 ，0 <= parent[i] <= n - 1 均成立
     * parent[0] == -1
     * parent 表示一棵有效的树
     * s 仅由小写英文字母组成
     *
     * @param parent 
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/8/13 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestPath(int[] parent, String s) {
        int result;
        result = method_01(parent, s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：parent = [-1,0,0,1,1,2], s = "abacbe"
     * 输出：3
     * 1. 还是dfs遍历, 自底向上递归, 当我们到达节点 x时, 以x为根节点的子树中选择两个最长的子树, 相加之和 + 1, 与 ans 比较大小
     * 2. 对于节点x的子树, 返回子树的 index(子树的下标) 和 length(子树的长度), 判断子树下标与节点x, s[index] 与 s[x] 是否相同
     * 3. 如果相同, 则修改子树长度为0, 如果不同, 使用 List<Integer> list, list.add(length);
     * 4. dfs方法递归返回值是 int[2], 0: index, 1: length
     * 5. just do it!
     * AC: 135ms(7.74%)/96.13MB(12.21%)
     * @param parent 
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/13 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int ans;
    private int method_01(int[] parent, String s) {
        List<Integer> [] graph = buildGraph(parent);
        ans = 0;
        dfs(0, -1, graph, s);
        return ans;
    }

    private int[] dfs(int node, int parent, List<Integer>[] graph, String s) {
        int length = 1;
        List<Integer> list = new ArrayList<>();
        char nodeC = s.charAt(node);
        for (int next : graph[node]) {
            int[] child = dfs(next, node, graph, s);
            if (s.charAt(child[0]) != nodeC) {
                list.add(child[1]);
            }
        }
        list.sort((a, b) -> b - a);
        if (list.size() >= 2) {
            ans = Math.max(ans, list.get(0) + list.get(1) + 1);
        }
        length += list.size() >= 1 ? list.get(0) : 0;
        ans = Math.max(ans, length);
        return new int[]{node, length};
    }

    private List<Integer>[] buildGraph(int[] parent) {
        int n = parent.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            graph[parent[i]].add(i);
        }
        return graph;
    }
}
