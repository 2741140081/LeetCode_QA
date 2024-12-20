package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/20 16:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2192 {
    private Deque<Integer> stack = new ArrayDeque<>();
    /**
     * @Description: [
     * 给你一个正整数 n ，它表示一个 有向无环图 中节点的数目，节点编号为 0 到 n - 1 （包括两者）。
     *
     * 给你一个二维整数数组 edges ，其中 edges[i] = [fromi, toi] 表示图中一条从 fromi 到 toi 的单向边。
     *
     * 请你返回一个数组 answer，其中 answer[i]是第 i 个节点的所有 祖先 ，这些祖先节点 升序 排序。
     *
     * 如果 u 通过一系列边，能够到达 v ，那么我们称节点 u 是节点 v 的 祖先 节点。
     * ]
     * @param n
     * @param edges
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/20 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<List<Integer>> result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description: [
     * <p>1. 因为是DAG, 所以必定存在入度为0的节点, 将入度为0的节点存放到栈中, 使用额外空间int[] inDegree 记录节点的入度数
     * <p>2. 既然没有环, 就不需要pre[] 标记, 而且会存在多个节点到同一个节点的情况, such as: 0 -> 1, 2 -> 1, 3 -> 1
     * <p>3. 刚刚想到问题, 因为每一次都是存储0 -> 1 -> 2, 而还有一条线路是3 -> 1 -> 2, 那么节点2的祖先就是0, 1, 3, 如果
     * 使用1, 2那么就需要去重, 排序等一系列操作, 放弃这个方法, 逆向DFS
     * <p>4. 例如0 -> 1 -> 2, 而还有一条线路是3 -> 1 -> 2, 遍历0 ~ n - 1, 找a[i] 的祖先节点, 2 <- 1 <- 0, 每次遍历用pre[]来存储状态
     * <p>]
     * @param n
     * @param edges
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/20 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int n, int[][] edges) {
        int[] inDegree = new int[n];
        List<Integer>[] list = new ArrayList[n];
        Arrays.fill(list, new ArrayList<>());

        for (int[] ints : edges) {
            int x = ints[0], y = ints[1];
            inDegree[y]++;
            list[x].add(y);
        }
        List<Integer>[] ans = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                stack.offerLast(i);
                DFSMaxArea(i, list, ans);
                stack.pollLast();
            }
        }
        return Arrays.asList(ans);
    }

    private void DFSMaxArea(int i, List<Integer>[] list, List<Integer>[] ans) {
        for (Integer j : list[i]) {
            stack.offerLast(j);
            List<Integer> tempList = new ArrayList<>(stack);
            Collections.sort(tempList);
            ans[j] = tempList;
            DFSMaxArea(j, list, ans);
            stack.pollLast();
        }
    }
}
