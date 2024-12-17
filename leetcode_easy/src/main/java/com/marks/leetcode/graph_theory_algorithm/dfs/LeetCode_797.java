package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 17:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_797 {
    private List<List<Integer>> ans = new ArrayList<>();
    private Deque<Integer> stack = new ArrayDeque<>();
    /**
     * @Description: [
     * 给你一个有 n 个节点的 有向无环图（DAG），请你找出所有从节点 0 到节点 n-1 的路径并输出（不要求按特定顺序）
     * graph[i] 是一个从节点 i 可以访问的所有节点的列表（即从节点 i 到节点 graph[i][j]存在一条有向边）。
     *
     * tips:
     * n == graph.length
     * 2 <= n <= 15
     * 0 <= graph[i][j] < n
     * graph[i][j] != i（即不存在自环）
     * graph[i] 中的所有元素 互不相同
     * 保证输入为 有向无环图（DAG）
     * ]
     * @param graph 
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/17 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result;
//        result = method_01(graph);
        result = method_02(graph);
        return result;
    }

    /**
     * @Description: [
     * 由于method_01的空间复杂度太高, 使用栈优化
     * AC:2ms/45.28MB
     * ]
     * @param graph
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/17 17:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_02(int[][] graph) {
        int n = graph.length;
        stack.offer(0);
        DFSMaxArea(0, graph, n - 1);
        return ans;
    }

    private void DFSMaxArea(int i, int[][] graph, int destination) {
        if (i == destination) {
            ans.add(new ArrayList<>(stack));
            return;
        }
        for (int num : graph[i]) {
            stack.offer(num);
            DFSMaxArea(num, graph, destination);
            stack.pollLast();
        }
    }

    /**
     * @Description: [
     * E1:输入：graph = [[4,3,1],[3,2,4],[3],[4],[]]
     * AC:2ms/45.69MB
     * ]
     * @param graph
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/17 17:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[][] graph) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        DFSMaxArea(0, graph, list);
        return ans;
    }
    private void DFSMaxArea(int i, int[][] graph, List<Integer> list) {
        if (i == graph.length - 1) {
            ans.add(list);
            return;
        }
        for (int j = 0; j < graph[i].length; j++) {
            List<Integer> tempList = new ArrayList<>();
            tempList.addAll(list);
            tempList.add(graph[i][j]);
            DFSMaxArea(graph[i][j], graph, tempList);
        }
    }
}
