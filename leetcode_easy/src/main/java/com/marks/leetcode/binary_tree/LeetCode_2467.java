package com.marks.leetcode.binary_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/4 16:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2467 {
    /**
     * @Description:
     * 一个 n 个节点的无向树，节点编号为 0 到 n - 1 ，树的根结点是 0 号节点。给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] ，表示节点 ai 和 bi 在树中有一条边。
     *
     * 在每一个节点 i 处有一扇门。同时给你一个都是偶数的数组 amount ，其中 amount[i] 表示：
     *
     * 如果 amount[i] 的值是负数，那么它表示打开节点 i 处门扣除的分数。
     * 如果 amount[i] 的值是正数，那么它表示打开节点 i 处门加上的分数。
     * 游戏按照如下规则进行：
     *
     * 一开始，Alice 在节点 0 处，Bob 在节点 bob 处。
     * 每一秒钟，Alice 和 Bob 分别 移动到相邻的节点。Alice 朝着某个 叶子结点 移动，Bob 朝着节点 0 移动。
     * 对于他们之间路径上的 每一个 节点，Alice 和 Bob 要么打开门并扣分，要么打开门并加分。注意：
     * 如果门 已经打开 （被另一个人打开），不会有额外加分也不会扣分。
     * 如果 Alice 和 Bob 同时 到达一个节点，他们会共享这个节点的加分或者扣分。换言之，如果打开这扇门扣 c 分，那么 Alice 和 Bob 分别扣 c / 2 分。如果这扇门的加分为 c ，那么他们分别加 c / 2 分。
     * 如果 Alice 到达了一个叶子结点，她会停止移动。类似的，如果 Bob 到达了节点 0 ，他也会停止移动。注意这些事件互相 独立 ，不会影响另一方移动。
     * 请你返回 Alice 朝最优叶子结点移动的 最大 净得分。
     *
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * edges 表示一棵有效的树。
     * 1 <= bob < n
     * amount.length == n
     * amount[i] 是范围 [-10^4, 10^4] 之间的一个 偶数 。
     * @param edges 
     * @param bob 
     * @param amount
     * @return int
     * @author marks
     * @CreateDate: 2025/8/4 16:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        int result;
        result = method_01(edges, bob, amount);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges = [[0,1],[1,2],[1,3],[3,4]], bob = 3, amount = [-2,4,2,-4,6]
     * 输出：6
     *
     * 1. bob 的移动路线是确定的, 但是我们需要确定这条线路才行, 确定这条线路之后
     * 2. 对这条线路进行处理, 如果线路中节点个数是偶数, 分成两段 0 ~ x1, x2 ~ bob, 将 x2 ~ bob 的amount[i] = 0
     * 3. 如果为奇数, 对公共点 x ~ bob, 其中x重置为 amount[x] = amount[x] >> 1;, 其他节点重置为0
     * 4. 通过bfs计算重新 更新后 amount[], 到叶子节点的最大值
     *
     * AC: 556ms(6.25%)/98.94MB(100%)
     * @param edges
     * @param bob
     * @param amount
     * @return int
     * @author marks
     * @CreateDate: 2025/8/4 16:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] edges, int bob, int[] amount) {
        int n = edges.length + 1;
        List<Integer>[] lists = new List[n];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
            set.add(i);
        }

        for (int[] edge : edges) {
            lists[edge[0]].add(edge[1]);
            lists[edge[1]].add(edge[0]);
        }

        Map<Integer, Integer> parent = new HashMap<>();

        parent.put(0, -1); // 相当于0节点作为根节点, 并且根节点没有父节点, -1 作为一个虚拟的父节点

        Queue<Integer> dfs = new LinkedList<>();
        dfs.add(0);
        set.remove(0);

        while (!dfs.isEmpty()) {
            int curr = dfs.poll();
            for (int next : lists[curr]) {
                if (!parent.containsKey(next)) {
                    parent.put(next, curr);
                    dfs.add(next);
                    set.remove(curr);
                }
            }
        }

        List<Integer> route = new ArrayList<>();
        int curr = bob;
        while (curr != -1) {
            route.add(curr);
            curr = parent.get(curr);
        }
        int size = route.size();
        if (size % 2 == 0) {
            for (int i = 0; i < route.size() / 2; i++) {
                amount[route.get(i)] = 0;
            }
        } else {
            // 奇数
            for (int i = 0; i < route.size() / 2; i++) {
                amount[route.get(i)] = 0;
            }
            int index = route.get(route.size() / 2);
            amount[index] = amount[index] >> 1;
        }

        int ans = Integer.MIN_VALUE;

        for (int s : set) {
            int count = 0;
            while (s != -1) {
                count += amount[s];
                s = parent.get(s);
            }
            ans = Math.max(count, ans);
        }

        return ans;
    }


}
