package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/30 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_802 {
    /**
     * @Description: [
     * <p>有一个有 n 个节点的有向图，节点按 0 到 n - 1 编号。
     * 图由一个 索引从 0 开始 的 2D 整数数组 graph表示， graph[i]是与节点 i 相邻的节点的整数数组，这意味着从节点 i 到 graph[i]中的每个节点都有一条边。
     *
     * <p>如果一个节点没有连出的有向边，则该节点是 终端节点 。如果从该节点开始的所有可能路径都通向 终端节点 ，则该节点为 安全节点 。
     *
     * <p>返回一个由图中所有 安全节点 组成的数组作为答案。答案数组中的元素应当按 升序 排列。
     * ]
     * @param graph 
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/30 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> result;
        result = method_01(graph);
        result = method_02(graph);
        return result;
    }

    /**
     * @Description: [
     * 不知道怎么理解了, 感觉有点糊涂了!!!
     * 直接看题解吧
     *
     * AC:14ms/54.29MB
     *
     * 为什么要inDegree[] 返回来计算不是很理解
     * <p> 根据题意，若一个节点没有出边，则该节点是安全的；若一个节点出边相连的点都是安全的，则该节点也是安全的。
     *
     * ]
     * @param graph
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/30 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_02(int[][] graph) {
        int n = graph.length;
        int[] inDegree = new int[n];
        List<Integer>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j : graph[i]) {
                inDegree[i]++;
                lists[j].add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            for (int j : lists[curr_i]) {
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                ans.add(i);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 思考的答案是, 我们不仅需要入度, 还需要出度, 入度为0或者出度为0, 则删除节点. 并且更新入度, 出度表
     *
     * 解答错误, Result:33/112
     * 错误实例: 存在自环
     * graph[][] = {{0}, {2, 3, 4}, {3, 4}, {0, 4}, {}};
     * 因为节点0 是一个自环节点, 所以任何能到达节点0的节点都需要删除,
     *
     * 解决思路为:
     * 1. 找到所有的自环节点
     * 2. 依据找到的自环节点进行BFS, 删除所有可以到达自环节点的节点
     *
     * 解答依旧错误:51/112
     * 感觉是我的理解有问题, 安全节点的理解!!!
     * ]
     * @param graph
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/30 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[][] graph) {
        int n = graph.length;
        int[] inDegree = new int[n];
        int[] outDegree = new int[n];
        List<Integer>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        List<Integer> loopList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j : graph[i]) {
                if (i == j) {
                    loopList.add(i);
                }
                inDegree[j]++;
                outDegree[i]++;
                lists[j].add(i);
            }
        }
        Set<Integer> connectedList = new HashSet<>();
        for (int i = 0; i < loopList.size(); i++) {
            BFSMaxArea(loopList.get(i), lists, connectedList);
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0 || outDegree[i] == 0) {
                queue.offer(i);
                set.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            /*
            curr_i 有3种情况
            1. in[i] == 0 && out[i] == 0;
            2. in[i] == 0 && out[i] != 0;
            3. in[i] != 0 && out[i] == 0;
            所以我们需要一个额外的二维空间来存储出度的邻接表
             */
            if (inDegree[curr_i] == 0 && outDegree[curr_i] != 0) {
                for (int j : graph[curr_i]) {
                    inDegree[j]--;
                    if (inDegree[j] == 0 && !set.contains(j)) {
                        queue.offer(j);
                        set.add(j);
                    }
                }
            } else if (inDegree[curr_i] != 0 && outDegree[curr_i] == 0) {
                for (int j : lists[curr_i]) {
                    outDegree[j]--;
                    if (outDegree[j] == 0 && !set.contains(j)) {
                        queue.offer(j);
                        set.add(j);
                    }
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int value : set) {
            if (!loopList.contains(value) && !connectedList.contains(value)) {
                ans.add(value);
            }
        }
        Collections.sort(ans);
        return ans;
    }

    private void BFSMaxArea(int i, List<Integer>[] lists, Set<Integer> connectedList) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);
        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            for (int j : lists[curr_i]) {
                if (!connectedList.contains(j)) {
                    connectedList.add(j);
                    queue.offer(j);
                }
            }
        }
    }
}
