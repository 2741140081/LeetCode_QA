package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1129 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 10:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1129 {

    /**
     * @Description:
     * 给定一个整数 n，即有向图中的节点数，其中节点标记为 0 到 n - 1。
     * 图中的每条边为红色或者蓝色，并且可能存在自环或平行边。
     *
     * 给定两个数组 redEdges 和 blueEdges，其中：
     * redEdges[i] = [ai, bi] 表示图中存在一条从节点 ai 到节点 bi 的红色有向边，
     * blueEdges[j] = [uj, vj] 表示图中存在一条从节点 uj 到节点 vj 的蓝色有向边。
     * 返回长度为 n 的数组 answer，其中 answer[X] 是从节点 0 到节点 X 的红色边和蓝色边交替出现的最短路径的长度。
     * 如果不存在这样的路径，那么 answer[x] = -1。
     * tips:
     * 1 <= n <= 100
     * 0 <= redEdges.length, blueEdges.length <= 400
     * redEdges[i].length == blueEdges[j].length == 2
     * 0 <= ai, bi, uj, vj < n
     * @param: n
     * @param: redEdges
     * @param: blueEdges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/12 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        int[] result;
        result = method_01(n, redEdges, blueEdges);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
     * 输出：[0,1,-1]
     * 1. 图论的第一步, 先将边转换为邻接矩阵, 分别将 redEdges 和 blueEdges 转换为邻接矩阵 List<Integer>[n] redMatrix 和 blueMatrix
     * 2. 我们这是一个有向图, 并且需要从0开始, 那么需要两个一维数组redDist 和 blueDist, redDist[i] 表示从0到i的最短距离, blueDist[i] 表示从0到i的最短距离
     * 3. 由于该图中存在自环和平行边, 因为每次移动或者向后移动, 花费都会加1, 所以需要判断当前节点花费 + 1是否 < 目标节点的花费,
     * 如果小于则添加到下一个处理节点, 如果不小于, 直接丢弃, redDist[i] = Math.min(redDist[i], redDist[cur] + 1);
     * 并且需要给节点一个初始值 INF = Integer.MAX_VALUE/2
     * 4. 用队列来进行广度优先搜索.
     * AC: 5ms/45.8MB
     * @param: n
     * @param: redEdges
     * @param: blueEdges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/12 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] redEdges, int[][] blueEdges) {
        final int INF = Integer.MAX_VALUE / 2; // 还是需要一个很大的初始值, 有可能会绕一圈，导致距离大于 n + 1, 所以需要一个比较大的值
        // 将集合边转换为邻接矩阵
        List<Integer>[] redMatrix = new List[n];
        List<Integer>[] blueMatrix = new List[n];
        for (int i = 0; i < n; i++) {
            redMatrix[i] = new ArrayList<>();
            blueMatrix[i] = new ArrayList<>();
        }
        for (int[] redEdge : redEdges) {
            redMatrix[redEdge[0]].add(redEdge[1]);
        }
        for (int[] blueEdge : blueEdges) {
            blueMatrix[blueEdge[0]].add(blueEdge[1]);
        }
        // 构建一维数组redDist 和 blueDist
        int[] redDist = new int[n];
        int[] blueDist = new int[n];
        Arrays.fill(redDist, INF);
        Arrays.fill(blueDist, INF);
        redDist[0] = 0;
        blueDist[0] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        // 添加初始节点
        queue.offer(0); // index, dist
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int index = queue.poll();
                // 遍历红色边, 前一条边需要蓝色边, dist = blueDist[index] + 1
                for (int next : redMatrix[index]) {
                    if (redDist[next] > blueDist[index] + 1) {
                        redDist[next] = blueDist[index] + 1;
                        queue.offer(next);
                    }
                }
                // 遍历蓝色边, 前一条边需要红色边, dist = redDist[index] + 1
                for (int next : blueMatrix[index]) {
                    if (blueDist[next] > redDist[index] + 1) {
                        blueDist[next] = redDist[index] + 1;
                        queue.offer(next);
                    }
                }
            }
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = Math.min(redDist[i], blueDist[i]);
            if (ans[i] >= INF) {
                ans[i] = -1;
            }
        }

        return ans;
    }

}
