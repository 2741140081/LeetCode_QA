package com.marks.leetcode.binary_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/1 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1377 {

    /**
     * @Description:
     * 给你一棵由 n 个顶点组成的无向树，顶点编号从 1 到 n。青蛙从 顶点 1 开始起跳。规则如下：
     *
     * 在一秒内，青蛙从它所在的当前顶点跳到另一个 未访问 过的顶点（如果它们直接相连）。
     * 青蛙无法跳回已经访问过的顶点。
     * 如果青蛙可以跳到多个不同顶点，那么它跳到其中任意一个顶点上的机率都相同。
     * 如果青蛙不能跳到任何未访问过的顶点上，那么它每次跳跃都会停留在原地。
     * 无向树的边用数组 edges 描述，其中 edges[i] = [ai, bi] 意味着存在一条直接连通 ai 和 bi 两个顶点的边。
     *
     * 返回青蛙在 t 秒后位于目标顶点 target 上的概率。与实际答案相差不超过 10-5 的结果将被视为正确答案。
     *
     * 1 <= n <= 100
     * edges.length == n - 1
     * edges[i].length == 2
     * 1 <= ai, bi <= n
     * 1 <= t <= 50
     * 1 <= target <= n
     * @param n 
     * @param edges 
     * @param t 
     * @param target
     * @return double
     * @author marks
     * @CreateDate: 2025/8/1 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double frogPosition(int n, int[][] edges, int t, int target) {
        double result;
        result = method_01(n, edges, t, target);
        result = method_02(n, edges, t, target);
        return result;
    }

    private double method_02(int n, int[][] edges, int t, int target) {
        List<Integer>[] lists = new List[n + 1];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }

        boolean[] used = new boolean[n + 1];

        for (int[] edge : edges) {
            lists[edge[0]].add(edge[1]);
            lists[edge[1]].add(edge[0]);
        }

        return dfs(lists, used, 1, t, target);
    }

    private double dfs(List<Integer>[] lists, boolean[] used, int i, int t, int target) {
        int next = i == 1 ? lists[i].size() : lists[i].size() - 1;

        if (t == 0 || next == 0) {
            return i == target ? 1.0 : 0.0;
        }

        double ans = 0.0;
        used[i] = true;
        for (int j : lists[i]) {
            if (!used[j]) {
                ans += dfs(lists, used, j, t - 1, target);
            }
        }
        return ans / next;
    }


    /**
     * @Description:
     * 1. 判断青蛙从 节点1 到 target 节点的最短距离, tMin, tMin > t, return = 0;
     * 2. tMin = t 或者 target 节点是叶子节点, 即该节点没有子节点, 原地蹦
     * 3. tMin > t 并且 target 不是叶子节点, return 0;
     *
     * E1:
     * n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4
     *
     * AC: 6ms(14.86%)/43.85MB(63.51%)
     * @param n
     * @param edges
     * @param t
     * @param target
     * @return double
     * @author marks
     * @CreateDate: 2025/8/1 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private List<Integer> nodes = new ArrayList<>();
    private double method_01(int n, int[][] edges, int t, int target) {
        double ans = 0;
        // 特殊处理原点情况
        if (target == 1 && n == 1) {
            ans = 1.0;
            return ans;
        } else if (target == 1 && n > 1) {
            return ans;
        }


        List<Integer>[] lists = new List[n + 1];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }

        Set<Integer> used = new HashSet<>();

        for (int[] edge : edges) {
            lists[edge[0]].add(edge[1]);
            lists[edge[1]].add(edge[0]);
        }

        // 通过BFS, 找到最短 tMin
        int tMin = 0;
        Queue<Integer> queueBFS = new LinkedList<>();
        queueBFS.add(1);
        used.add(1);

        boolean flag = true;
        Map<Integer, List<Integer>> map = new HashMap<>(); // 存储节点的子节点数量

        while (!queueBFS.isEmpty() && flag) {
            int size = queueBFS.size();
            for (int i = 0; i < size; i++) {
                int curr = queueBFS.poll();
                for (int next : lists[curr]) {
                    if (next == target) {
                        flag = false;
                    }
                    if (!used.contains(next)) {
                        map.computeIfAbsent(curr, k -> new ArrayList<>()).add(next);
                        queueBFS.add(next);
                        used.add(next);
                    }
                }
            }
            tMin++;
        }



        // 判断 tMin 与 t 的大小关系
        if (tMin > t) {
            return ans;
        } else if (tMin < t) {
            // tMin > t
            int count = 0;
            for (int next : lists[target]) {
                if (!used.contains(next)) {
                    count++;
                }
            }
            if (count > 0) {
                return ans;
            }
        }

        // 必定存在可能性, 即可能性大于0
        dfsGetNode(1, target,map);
        ans = 1.0;
        double num = 1.0;
        for (int i = 0; i < nodes.size(); i++) {
            double size = map.get(nodes.get(i)).size();
            ans *= (num / size);
        }
        System.out.println(ans + " <====result");
        return ans;
    }

    private int dfsGetNode(int parent, int target, Map<Integer, List<Integer>> map) {
        if (parent == target) {
            return 1;
        }

        int count = 0;
        if (map.containsKey(parent)) {
            // 递归处理
            for (int next : map.get(parent)) {
                count += dfsGetNode(next, target, map);
            }
        }

        if (count == 1) {
            nodes.add(parent);
        }
        return count;
    }
}
