package com.marks.leetcode.binary_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/30 16:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1443 {

    /**
     * @Description:
     * 给你一棵有 n 个节点的无向树，节点编号为 0 到 n-1 ，它们中有一些节点有苹果。
     * 通过树上的一条边，需要花费 1 秒钟。
     * 你从 节点 0 出发，请你返回最少需要多少秒，可以收集到所有苹果，并回到节点 0 。
     *
     * 无向树的边由 edges 给出，其中 edges[i] = [fromi, toi] ，表示有一条边连接 from 和 toi 。
     * 除此以外，还有一个布尔数组 hasApple ，其中 hasApple[i] = true 代表节点 i 有一个苹果，否则，节点 i 没有苹果。
     *
     * 1 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai < bi <= n - 1
     * hasApple.length == n
     * @param n 
     * @param edges 
     * @param hasApple
     * @return int
     * @author marks
     * @CreateDate: 2025/7/30 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        int result;
//        result = method_01(n, edges, hasApple);
        result = method_02(n, edges, hasApple);
        result = method_03(n, edges, hasApple);
        return result;
    }


    public static final int MAX_N = 100005;

    List<Integer>[] g = new List[MAX_N];
    int[] apple = new int[MAX_N];
    int ans;
    /**
     * @Description:
     * 查看官解
     * @param n
     * @param edges
     * @param hasApple
     * @return int
     * @author marks
     * @CreateDate: 2025/7/30 17:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int n, int[][] edges, List<Boolean> hasApple) {
        for (int i = 0; i < MAX_N; ++i) {
            g[i] = new ArrayList<Integer>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            g[u].add(v);
            g[v].add(u);
        }

        getAppleNumber(0, -1, hasApple);

        ans = 0;
        dfs(0, -1);

        return ans;
    }

    public int getAppleNumber(int o, int from, List<Boolean> hasApple) {
        apple[o] = hasApple.get(o) ? 1 : 0;
        for (int son : g[o]) {
            if (son == from) {
                continue;
            }
            apple[o] += getAppleNumber(son, o, hasApple);
        }
        return apple[o];
    }

    public void dfs(int o, int from) {
        for (int son : g[o]) {
            if (son == from) {
                continue;
            }
            if (apple[son] != 0) {
                ans += 2;
                dfs(son, o);
            }
        }
    }

    /**
     * @Description:
     * 需要自底向上会比较好做
     * 1. 错误了, queue中只能添加呢些最底层的
     * @param n
     * @param edges
     * @param hasApple
     * @return int
     * @author marks
     * @CreateDate: 2025/7/30 16:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[][] edges, List<Boolean> hasApple) {
        List<Integer>[] lists = new List[n];
        List<Integer>[] trees = new List[n];
        Set<Integer> used = new HashSet<>();
        List<Integer> appleId = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
            trees[i] = new ArrayList<>();
            if (hasApple.get(i) && i != 0) {
                appleId.add(i);
            }
        }

        if (appleId.size() == 0) {
            // 即一个苹果都没有
            return 0;
        }

        for (int[] edge : edges) {
            lists[edge[1]].add(edge[0]);
            trees[edge[0]].add(edge[1]);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int id : appleId) {
            boolean flag = dfs(id, trees, appleId);
            if (flag) {
                queue.add(id);
            }
        }

        int ans = 0;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : lists[curr]) {
                if (!used.contains(next)) {
                    ans += 2;
                    used.add(next);
                    queue.add(next);
                } else {
                    ans += 2;
                }
            }
        }

        return ans;
    }

    private boolean dfs(int node, List<Integer>[] trees, List<Integer> list) {
        for (Integer next : trees[node]) {
            if (list.contains(next)) {
                // 相当于当前节点的子节点存在有苹果的节点, 所以此节点不能添加到queue中
                return false;
            } else {
                if (!dfs(next, trees, list)) {
                    return false;
                }
            }
        }
        // 相当于当前节点就是叶子节点, 没有下一个节点, 直接返回true.
        return true;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]
     * 输出：8
     * 解释：上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。
     *
     * 1. 将无向图转为邻接表
     * 2. 从节点0 出发, 使用BFS, 相当于每到达一个hasApple节点, 就把time给加上, 并且总的time + 2, 然后将time 重置为0
     *
     * 此方法不可行, 如A节点没有苹果, 但是A节点的子节点都有苹果, 会重复计算到达A节点的距离
     *
     * 需要递归计算, 见method_02
     *
     * @param n 
     * @param edges 
     * @param hasApple 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/30 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, List<Boolean> hasApple) {
        List<Integer>[] lists = new List[n];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
            if (hasApple.get(i)) {
                set.add(i);
            }
        }
        if (set.size() == 0) {
            // 即一个苹果都没有
            return 0;
        }

        for (int[] edge : edges) {
            lists[edge[0]].add(edge[1]);
        }

        int ans = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 1}); // 0表示节点, 1表示到达下一个节点所需1s

        // 提前判断节点0是否有苹果
        if (hasApple.get(0)) {
            set.remove(0);
        }

        // BFS, set 如果为空, 表示剩余的节点没有了苹果, 可以直接退出
        while (!queue.isEmpty() && !set.isEmpty()) {
            int[] curr = queue.poll();
            int currId = curr[0];
            int preTime = curr[1];

            if (set.contains(currId)) {
                // 收获当前节点的苹果, 并且 add 时间
                ans += preTime * 2;
                preTime = 0;
                set.remove(currId);
            }

            for (int next : lists[currId]) {
                queue.add(new int[]{next, preTime + 1});
            }
        }
        return ans;
    }
}
