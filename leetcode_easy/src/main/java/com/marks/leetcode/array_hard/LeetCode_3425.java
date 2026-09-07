package com.marks.leetcode.array_hard;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3425 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/4 17:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3425 {

    /**
     * @Description:
     * 给你一棵根节点为节点 0 的无向树，树中有 n 个节点，编号为 0 到 n - 1 ，
     * 这棵树通过一个长度为 n - 1 的二维数组 edges 表示，其中
     * edges[i] = [ui, vi, lengthi] 表示节点 ui 和 vi 之间有一条长度为 lengthi 的边。
     * 同时给你一个整数数组 nums ，其中 nums[i] 表示节点 i 的值。
     * 特殊路径 指的是树中一条从祖先节点 往下 到后代节点且经过节点的值 互不相同 的路径。
     * 注意 ，一条路径可以开始和结束于同一节点。
     * 请你返回一个长度为 2 的数组 result ，其中 result[0] 是 最长 特殊路径的 长度 ，result[1] 是所有 最长特殊路径中的 最少 节点数目。
     *
     * tips:
     * 2 <= n <= 5 * 10^4
     * edges.length == n - 1
     * edges[i].length == 3
     * 0 <= ui, vi < n
     * 1 <= lengthi <= 10^3
     * nums.length == n
     * 0 <= nums[i] <= 5 * 10^4
     * 输入保证 edges 表示一棵合法的树。
     * @param: edges
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/04 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int[] result;
        result = method_01(edges, nums);
        result = method_02(edges, nums);
        return result;
    }


    private int maxLen = -1;
    private int minNodes = 0;

    private int[] method_02(int[][] edges, int[] nums) {
        List<int[]>[] g = new ArrayList[nums.length];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            int w = e[2];
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        List<Integer> dis = new ArrayList<>();
        dis.add(0);
        // 颜色 -> 该颜色最近一次出现的深度 +1，注意这里已经 +1 了
        Map<Integer, Integer> lastDepth = new HashMap<>();
        dfs(0, -1, 0, g, nums, dis, lastDepth);
        return new int[]{maxLen, minNodes};
    }

    private void dfs(int x, int fa, int topDepth, List<int[]>[] g, int[] nums, List<Integer> dis, Map<Integer, Integer> lastDepth) {
        int color = nums[x];
        int oldDepth = lastDepth.getOrDefault(color, 0);
        topDepth = Math.max(topDepth, oldDepth);

        int disX = dis.get(dis.size() - 1);
        int len = disX - dis.get(topDepth);
        int nodes = dis.size() - topDepth;
        if (len > maxLen || len == maxLen && nodes < minNodes) {
            maxLen = len;
            minNodes = nodes;
        }

        lastDepth.put(color, dis.size());
        for (int[] e : g[x]) {
            int y = e[0];
            if (y != fa) { // 避免访问父节点
                dis.add(disX + e[1]);
                dfs(y, x, topDepth, g, nums, dis, lastDepth);
                dis.remove(dis.size() - 1); // 恢复现场
            }
        }
        lastDepth.put(color, oldDepth); // 恢复现场
    }


    private int[] ans;
    private Deque<Integer> path;
    private Set<Integer> nodeKey;
    private Map<Integer, Integer> lenMap;

    /**
     * @Description:
     * 1. 树的定义为无环图, 并且每个节点只连接一个父节点, 除根节点外
     * 2. 由于特殊路径的定义是, 祖先节点到后代节点的路径, 并且路径上节点值不同, 所以需要存储节点的值。
     * 3. 由于可以是部分节点来构成特殊路径，length_i > 0 的数, 所以 特殊路径的子路径必定不是最长的特殊路径
     * 4. 当在查找特殊路径的过程中, 如果添加节点 i 之后, 此时路径中存在重复值, 需要通过滑动窗口的形式, 缩小
     * 窗口, 使得特殊路径中不包含重复元素, 并且记录此时的长度和节点数目
     * 5. 采用深度优先搜索, 并且在处理节点时, 需要将剔除的节点存储起来, 以便后续恢复
     * 6. 需要用什么数据结构来存储节点, 需要有序, 按照 FIFO 顺序, 并且还需要判断节点是否重复, 添加队列和Set
     * 超时 674/675
     * 7. 需要分析时间复杂度
     * 8. 恢复现场的时间过高,
     * @param: edges
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/04 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges, int[] nums) {
        ans = new int[2];
        ans[1] = 1;
        path = new ArrayDeque<>();
        nodeKey = new HashSet<>();
        lenMap = new HashMap<>();
        // 通过 edges 构建邻接表
        int n = edges.length + 1;
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int length = edge[2];
            adj[u].add(new int[]{v, length});
            adj[v].add(new int[]{u, length});
            // 将 u * 10^5 + v 和 v * 10^5 + u 合并成一个 key
            int key = u * 100000 + v;
            lenMap.put(key, length);
            key = v * 100000 + u;
            lenMap.put(key, length);
        }
        // 执行 DFS
        path.offer(0);
        nodeKey.add(nums[0]);
        dfs(0, -1, 0, 1, nums, adj);

        return ans;
    }

    private void dfs(int curr, int parent, int sum, int count, int[] nums, List<int[]>[] adj) {
        if (sum > ans[0]) {
            ans[0] = sum;
            ans[1] = count;
        } else if (sum == ans[0]) {
            ans[1] = Math.min(ans[1], count);
        }

        for (int[] neighbor : adj[curr]) {
            int next = neighbor[0];
            int length = neighbor[1];
            if (next != parent) {
                // 判断当前节点是否重复, 如果重复则需要滑动窗口
                Deque<int[]> delete = new ArrayDeque<>();
                while (nodeKey.contains(nums[next])) {
                    int idx = path.poll(); // {node, len}
                    int key = idx * 100000 + (path.isEmpty() ? next : path.peek());
                    int len = lenMap.get(key);
                    delete.add(new int[]{idx, len});
                    nodeKey.remove(nums[idx]);
                    sum -= len;
                    count--;
                }
                path.offer(next);
                nodeKey.add(nums[next]);
                dfs(next, curr, sum + length, count + 1, nums, adj);
                path.removeLast();
                nodeKey.remove(nums[next]);
                while (!delete.isEmpty()) {
                    // 需要添加在头部
                    int[] idx = delete.pollLast();
                    path.addFirst(idx[0]);
                    nodeKey.add(nums[idx[0]]);
                    sum += idx[1];
                    count++;
                }
            }
        }
    }

}
