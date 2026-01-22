package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3241 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3241 {


    /**
     * @Description:
     * 给你一棵 无向 树，树中节点从 0 到 n - 1 编号。
     * 同时给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示节点 ui 和 vi 在树中有一条边。
     * 一开始，所有 节点都 未标记 。对于节点 i ：
     * 当 i 是奇数时，如果时刻 x - 1 该节点有 至少 一个相邻节点已经被标记了，那么节点 i 会在时刻 x 被标记。
     * 当 i 是偶数时，如果时刻 x - 2 该节点有 至少 一个相邻节点已经被标记了，那么节点 i 会在时刻 x 被标记。
     * 请你返回一个数组 times ，表示如果你在时刻 t = 0 标记节点 i ，那么时刻 times[i] 时，树中所有节点都会被标记。
     *
     * 请注意，每个 times[i] 的答案都是独立的，即当你标记节点 i 时，所有其他节点都未标记。
     * tips:
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= edges[i][0], edges[i][1] <= n - 1
     * 输入保证 edges 表示一棵合法的树。
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/22 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] timeTaken(int[][] edges) {
        int[] result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges = [[0,1],[0,2]]
     * 输出：[2,4,3]
     * 1. 以E1为例, 对于节点0, 0 是偶数节点, t = 0 时, 标记0; i = 1 时, i 是奇数, x - 1 = 0 => x = 1; i = 2 时, x - 2 = 0, x = 2; 所以 times[0] = 2
     * 2. 这种就可以得到一个结果数组 ans = [0, 1, 2], 然后我们执行换根处理, 节点的根由0 -> 1; 此时 i = 1, t = 0; i = 0, t += 2 => t = 2; i = 2, t += 2 => t = 4;
     * 3. 根据节点i的奇偶性可以, 假设 i 是奇数节点(不是起始节点, 假设0是起始节点)
     * 4. 怎么执行换根是一个大问题? int[] maxTime = new int[n]; // maxTime[i]表示节点i 在进行递归过程中的最大时间(maxTime)
     * 5. 应该需要保存最大时间和次大时间两个值, 并且保存最大时间下的索引id. 修改maxTime 的结构为二维数组 int[][] maxTime = new int[n][3]
     * AC: 277ms/279.92MB
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/22 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges) {
        // 构建邻接表
        int n = edges.length + 1;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        int[][] maxTime = new int[n][3]; // maxTime[i] = {最大子树时间, 次大子树时间, 最大子树时间下的子树编号}
        // 从节点0开始执行深度优先搜索(递归实现)
        dfs(0, -1, graph, maxTime); // 因为0 是起始节点, 所以最大时间减2
        int[] ans = new int[n]; // 结果数组
        reRoot(0, -1, 0, maxTime, graph, ans); // 换根处理, fromUp 表示从上一个节点
        return ans;
    }



    private int dfs(int i, int fa, List<Integer>[] graph, int[][] maxTime) {
        int max1 = 0; // 最大子树时间
        int max2 = 0; // 次大子树时间
        int index = -1;

        for (int next : graph[i]) {
            if (next == fa) {
                continue;
            }
            int max = dfs(next, i, graph, maxTime);
            if (max > max1) {
                max2 = max1;
                max1 = max;
                index = next;
            } else if (max > max2) {
                max2 = max;
            }
        }

        maxTime[i][0] = max1;
        maxTime[i][1] = max2;
        maxTime[i][2] = index;

        return max1 + (2 - i % 2);
    }

    private void reRoot(int i, int fa, int fromUp, int[][] maxTime, List<Integer>[] graph, int[] ans) {
        int max1 = maxTime[i][0];
        int max2 = maxTime[i][1];
        int index = maxTime[i][2]; // 最大时间的索引
        ans[i] = Math.max(max1, fromUp);
        // 遍历i 的子节点
        for (int next : graph[i]) {
            if (next == fa) {
                continue;
            }
            // 处理当前节点
            int max = (index == next ? max2 : max1);
            int value = 2 - i % 2; // 从 i 向 next 移动耗时(取 i 的奇偶性)
            reRoot(next, i, Math.max(fromUp, max) + value, maxTime, graph, ans);
        }
    }
}
