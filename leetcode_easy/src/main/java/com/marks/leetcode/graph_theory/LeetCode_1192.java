package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1192 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 11:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1192 {
    private int[] discoveryTime;
    private int[] lowTime;
    private int time;
    private boolean[] visited;
    private List<Integer>[] matrix;
    private List<List<Integer>> bridges;

    /**
     * @Description:
     * 力扣数据中心有 n 台服务器，分别按从 0 到 n-1 的方式进行了编号。
     * 它们之间以 服务器到服务器 的形式相互连接组成了一个内部集群，连接是无向的。
     * 用  connections 表示集群网络，connections[i] = [a, b] 表示服务器 a 和 b 之间形成连接。
     * 任何服务器都可以直接或者间接地通过网络到达任何其他服务器。
     * 关键连接 是在该集群中的重要连接，假如我们将它移除，便会导致某些服务器无法访问其他服务器。
     * 请你以任意顺序返回该集群内的所有 关键连接 。
     * tips:
     * 2 <= n <= 10^5
     * n - 1 <= connections.length <= 10^5
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * 不存在重复的连接
     * @param: n
     * @param: connections
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/12 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> result;
//        result = method_01(n, connections);
        result = method_02(n, connections);
        return result;
    }

    /**
     * @Description:
     * E1:
     * int n = 6;
     * int[][] connections = {{0, 1}, {1, 2}, {2, 0}, {1, 3}, {3, 4}, {4, 5}, {5, 3}};
     * 使用 Tarjan算法 来求出关键连接(桥 / 切边)
     * 1. 构建邻接矩阵
     * 2. int[] discoveryTime, visited, lowTime
     * AC: 74ms/215.66MB
     * @param: n
     * @param: connections
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/12 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_02(int n, List<List<Integer>> connections) {
        this.discoveryTime = new int[n];
        this.lowTime = new int[n];
        // 给discoveryTime, lowTime 初始化为 -1
        Arrays.fill(discoveryTime, -1);
        Arrays.fill(lowTime, -1);
        this.time = 0;
        this.visited = new boolean[n];
        this.matrix = new List[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new ArrayList<>();
        }
        for (List<Integer> connection : connections) {
            int a = connection.get(0);
            int b = connection.get(1);
            matrix[a].add(b);
            matrix[b].add(a);
        }
        bridges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // 使用深度优先搜索
                dfs(i, -1);
            }
        }

        return bridges;
    }

    private void dfs(int curr, int parent) {
        visited[curr] = true;
        discoveryTime[curr] = lowTime[curr] = time++;
        for (int next : matrix[curr]) {
            if (next == parent) {
                continue;
            }

            if (!visited[next]) {
                dfs(next, curr);
                lowTime[curr] = Math.min(lowTime[curr], lowTime[next]);
                if (lowTime[next] > discoveryTime[curr]) {
                    // 如果下一个节点时间大于当前节点时间, 表示该节点是一个关键连接
                    bridges.add(Arrays.asList(curr, next));
                }
            } else {
                // 更新节点时间
                lowTime[curr] = Math.min(lowTime[curr], discoveryTime[next]);
            }
        }
    }

    /**
     * @Description:
     * 1. 构建邻接矩阵
     * 2. 我们应该需要把环给找出来, 并且得到环的节点。
     * 3. 使用一维数组标记已经访问的节点, 避免重复访问, curr表示当前节点, next 表示下一个节点, curr != next
     * 并且 visit[next] == true, 表示该节点已经访问了, 那么这个节点必定存在与环上
     * 4. 不太对, 可能存在多个环路
     * 5. 还是需要根据入度和出度来判断, 头节点的入度必定为1, 头节点相当于是可以删除的节点,
     * 6. 根据connections[] 数组构建入度inDegree(出度不需要, 因为我们是一个无向图), 把入度为1的节点添加到队列
     * 因为如果该节点在环上, 那么这个节点的入度永远大于1, 这个节点永远不会被删除。
     * 7. 遍历队列, 获取待删除的节点, 遍历节点的邻接表, 获取下一个节点, 更新下一个节点的入度, 如果下一个节点的入度 == 1, 添加到队列中
     * 8. 重复上述过程，List<List<Integer>> ans 存储curr -> next 这个结果
     * 9. 还存在一个问题, 我可能会重复删除边, 需要用Set<Integer> 来判断, int key = Math.min(curr, next) * 10^6 + Math.max(curr, next);
     * 10. 存在问题, 无法解决以下案例, 存在两个环, 环之间通过一条线连接, 这条线可以被删除, 但是线两端的节点入度都不是1. 看看官解吧
     * 11. 题解使用了Tarjan算法, 见method_02

     * @param: n
     * @param: connections
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/12 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int n, List<List<Integer>> connections) {
        // 构建邻接矩阵
        List<Integer>[] matrix = new List[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new ArrayList<>();
        }
        // 构建入度表
        int[] inDegree = new int[n];
        for (List<Integer> connection : connections) {
            int a = connection.get(0);
            int b = connection.get(1);
            matrix[a].add(b);
            matrix[b].add(a);
            inDegree[a]++;
            inDegree[b]++;
        }
        // 构建队列, 存储入度为1的节点
        Queue<Integer> queue = new ArrayDeque<>();
        // 构建List<List<Integer>> ans, 存储删除的边
        List<List<Integer>> ans = new ArrayList<>();
        // Set<Integer> set 判断是否删除重复边
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 1) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : matrix[curr]) {
                inDegree[next]--;
                if (inDegree[next] == 1) {
                    queue.offer(next);
                }
                // 向ans添加结果
                int key = Math.max(curr, next) * 1_000_000 + Math.min(curr, next);
                if (!set.contains(key)) {
                    ans.add(List.of(curr, next));
                    set.add(key);
                }
            }
        }
        return ans;
    }
}
