package com.marks.leetcode.graph_theory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_785 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/17 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_785 {

    /**
     * @Description:
     * 存在一个 无向图 ，图中有 n 个节点。其中每个节点都有一个介于 0 到 n - 1 之间的唯一编号。
     * 给你一个二维数组 graph ，其中 graph[u] 是一个节点数组，由节点 u 的邻接节点组成。
     * 形式上，对于 graph[u] 中的每个 v ，都存在一条位于节点 u 和节点 v 之间的无向边。
     * 该无向图同时具有以下属性：
     * 不存在自环（graph[u] 不包含 u）。
     * 不存在平行边（graph[u] 不包含重复值）。
     * 如果 v 在 graph[u] 内，那么 u 也应该在 graph[v] 内（该图是无向图）
     * 这个图可能不是连通图，也就是说两个节点 u 和 v 之间可能不存在一条连通彼此的路径。
     * 二分图 定义：如果能将一个图的节点集合分割成两个独立的子集 A 和 B ，并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，就将这个图称为 二分图 。
     *
     * 如果图是二分图，返回 true ；否则，返回 false 。
     * tips:
     * graph.length == n
     * 1 <= n <= 100
     * 0 <= graph[u].length < n
     * 0 <= graph[u][i] <= n - 1
     * graph[u] 不会包含 u
     * graph[u] 的所有值 互不相同
     * 如果 graph[u] 包含 v，那么 graph[v] 也会包含 u
     * @param: graph
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/17 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isBipartite(int[][] graph) {
        boolean result;
        result = method_01(graph);
        result = method_02(graph);
        return result;
    }

    /**
     * @Description:
     * 查看官方题解: 使用类似于染色法
     * AC: 5ms/46.34MB
     * @param: graph
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/17 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(int[][] graph) {
        int n = graph.length;
        // 将graph转换为邻接表
        List<Integer>[] graphList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graphList[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int next : graph[i]) {
                graphList[i].add(next);
            }
        }
        int[] visited = new int[n]; // 0: 未访问, 1: 集合A, 2: 集合B

        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                // 广度优先搜索
                Queue<Integer> queue = new ArrayDeque<>();
                queue.offer(i);
                visited[i] = 1;
                while (!queue.isEmpty()) {
                    int size = queue.size();
                    // 提取整个层
                    for (int j = 0; j < size; j++) {
                        int node = queue.poll();
                        int currValue = visited[node]; // 前一个的颜色
                        int nextValue = currValue == 1 ? 2 : 1;
                        for (int next : graph[node]) {
                            if (visited[next] == 0) {
                                visited[next] = nextValue;
                                queue.offer(next);
                            } else if (visited[next] != nextValue) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * @Description:
     * 1. 集合A和集合B, 集合的内部的元素直接不能存在连接,
     * 2. 遍历graph, 不需要构建邻接表, 因为graph 已经是邻接表了, boolean[] visited 存储元素是否访问过,
     * 3. 通过广度优先搜索, 遍历邻接表, 从节点0开始
     * 4. 由于图可能不是连通图, 所以存在多个图的可能性, 通过visited数组判断是否访问过, 如果访问过则跳过, 如果没有访问, 则进行广度优先搜索
     * 5. List<Integer>[2] lists 分别存储集合A和集合B的元素, 通过 int curr, prev 来交替
     * AC: 12ms/46.67MB
     * 时间复杂度应该可以降低, 有没有O(1) 时间可以判断 next 和 集合A或者B的元素是否存在连接
     * 6. 查看官解后使用类似与涂颜色的方法优化, 见 method_02
     * @param: graph
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/17 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] graph) {
        int n = graph.length;
        // 将graph转换为邻接表
        List<Integer>[] graphList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graphList[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int next : graph[i]) {
                graphList[i].add(next);
            }
        }
        boolean[] visited = new boolean[n];
        int curr = 0, prev = 1;
        List<Integer>[] lists = new ArrayList[2];
        lists[0] = new ArrayList<>();
        lists[1] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // 广度优先搜索
                Queue<Integer> queue = new ArrayDeque<>();
                queue.offer(i);
                visited[i] = true;
                lists[prev].add(i);
                while (!queue.isEmpty()) {
                    int size = queue.size();
                    // 提取整个层
                    for (int j = 0; j < size; j++) {
                        int node = queue.poll();
                        for (int next : graph[node]) {
                            if (!visited[next]) {
                                visited[next] = true;
                                queue.offer(next);
                                // 需要判断集合内的元素是否与 next 存在直接连接
                                boolean flag = false;
                                for (int num : lists[curr]) {
                                    if (graphList[num].contains(next) || graphList[next].contains(num)) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) {
                                    return false;
                                }
                                lists[curr].add(next);
                            }
                        }
                    }
                    curr = (curr + 1) % 2;
                    prev = (prev + 1) % 2;
                }
            }
        }

        return true;
    }

}
