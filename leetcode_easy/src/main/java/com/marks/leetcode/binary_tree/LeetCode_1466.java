package com.marks.leetcode.binary_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/25 17:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1466 {

    /**
     * @Description:
     * n 座城市，从 0 到 n-1 编号，其间共有 n-1 条路线。
     * 因此，要想在两座不同城市之间旅行只有唯一一条路线可供选择（路线网形成一颗树）。去年，交通运输部决定重新规划路线，以改变交通拥堵的状况。
     * 路线用 connections 表示，其中 connections[i] = [a, b] 表示从城市 a 到 b 的一条有向路线。
     * 今年，城市 0 将会举办一场大型比赛，很多游客都想前往城市 0 。
     * 请你帮助重新规划路线方向，使每个城市都可以访问城市 0 。返回需要变更方向的最小路线数。
     * 题目数据 保证 每个城市在重新规划路线方向后都能到达城市 0 。
     *
     * tips:
     * 2 <= n <= 5 * 10^4
     * connections.length == n-1
     * connections[i].length == 2
     * 0 <= connections[i][0], connections[i][1] <= n-1
     * connections[i][0] != connections[i][1]
     * @param n
     * @param connections
     * @return int
     * @author marks
     * @CreateDate: 2025/7/25 17:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minReorder(int n, int[][] connections) {
        int result;
        result = method_01(n, connections);
        return result;
    }

    /**
     * @Description:
     * 1. 将这个邻接表进行遍历, 查看可以分成多少个模块
     * 2. 或者通过入度和出度进行判断, 如果是一颗树, 那么0号节点的入度为0, 出度为x, 其他每个节点都有且仅有一个入度x出度,
     * 3. 不可能成环, 因为成环需要n个节点, 并且n条以上的边, 当前仅有n - 1 条边, 所以无法成环
     * 针对2, 存在问题, 0号节点的入度为 N 出度为0, 因为我们需要所有节点都可以到达节点 0, 其他节点的入度为N, 出度为 1
     *
     * 4. 通过广度优先搜索来处理, 即将connections[][] 遍历成一个邻接表, 然后bfs遍历
     * 4.1 由节点0 开始遍历
     *
     * AC: 72ms(23.38%)/72.94MB(24.17%)
     * @param n
     * @param connections
     * @return int
     * @author marks
     * @CreateDate: 2025/7/25 17:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] connections) {
        Set<Integer> used = new HashSet<>();
        List<Integer>[] inDegree = new List[n];
        List<Integer>[] outDegree = new List[n];
        for (int i = 0; i < n; i++) {
            inDegree[i] = new ArrayList<>();
            outDegree[i] = new ArrayList<>();
        }

        for (int[] connection : connections) {
            int start = connection[0];
            int end = connection[1];
            inDegree[start].add(end);
            outDegree[end].add(start);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        used.add(0);
        int ans = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            // 将当前节点的出度变成入度
            for (int next : outDegree[curr]) {
                if (!used.contains(next)) {
                    queue.add(next);
                    used.add(next);
                }
            }

            // 向队列添加下一个节点
            for (int next : inDegree[curr]) {
                if (!used.contains(next)) {
                    queue.add(next);
                    used.add(next);
                    ans++;
                }
            }
        }

        return ans;
    }
}
