package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/4 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3067 {

    /**
     * @Description:
     * 给你一棵无根带权树，树中总共有 n 个节点，分别表示 n 个服务器，服务器从 0 到 n - 1 编号。
     * 同时给你一个数组 edges ，其中 edges[i] = [ai, bi, weighti] 表示节点 ai 和 bi 之间有一条双向边，边的权值为 weighti 。
     * 再给你一个整数 signalSpeed 。
     *
     * 如果两台服务器 a 和 b 是通过服务器 c 可连接的，则：
     *
     * a < b ，a != c 且 b != c 。
     * 从 c 到 a 的距离是可以被 signalSpeed 整除的。
     * 从 c 到 b 的距离是可以被 signalSpeed 整除的。
     * 从 c 到 b 的路径与从 c 到 a 的路径没有任何公共边。
     * 请你返回一个长度为 n 的整数数组 count ，其中 count[i] 表示通过服务器 i 可连接 的服务器对的 数目 。
     *
     * tips:
     * 2 <= n <= 1000
     * edges.length == n - 1
     * edges[i].length == 3
     * 0 <= ai, bi < n
     * edges[i] = [ai, bi, weighti]
     * 1 <= weighti <= 10^6
     * 1 <= signalSpeed <= 10^6
     * 输入保证 edges 构成一棵合法的树。
     * @param edges
     * @param signalSpeed
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/4 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int[] result;
        result = method_01(edges, signalSpeed);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges = [[0,1,1],[1,2,5],[2,3,13],[3,4,9],[4,5,2]], signalSpeed = 1
     * 输出：[0,4,6,6,4,0]
     * 解释：由于 signalSpeed 等于 1 ，count[c] 等于所有从 c 开始且没有公共边的路径对数目。
     * 在输入图中，count[c] 等于服务器 c 左边服务器数目乘以右边服务器数目。
     *
     * 1. 通过服务器i, 连接服务器 a 和 b, 则节点i不能是叶子节点, 也就是至少两条的连接边, 叶子节点的count[i] = 0
     * 2. 将不同连接边看做是一个集群, 不同的集群之间才能进行配对, 因为不能有公共边
     * 3. 对集群进行筛选, 要求集群中点的距离是signalSpeed的倍数, 将不符合的集群点, 以及空的集群进行去除。
     * 4. 剩余的集群进行配对, 对于剩余的集群数量, 如果数量小于2, 则count[i] = 0, 如果大于2, 则使用后缀和进行计算 count[i] = pairSum(int[] array);
     * 5. 但是针对每个节点的集群计算, 感觉只能暴力计算
     *
     * AC: 169ms(41.82%)/45.09MB(5.45%)
     * @param edges
     * @param signalSpeed
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/4 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int n;
    private int[] method_01(int[][] edges, int signalSpeed) {
        n = edges.length + 1;
        List<int[]>[] lists = new List[n];

        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            lists[edge[0]].add(new int[]{edge[1], edge[2]});
            lists[edge[1]].add(new int[]{edge[0], edge[2]});
        }

        int[] ans = new int[n];
        for (int i = 0; i < lists.length; i++) {
            if (lists[i].size() < 2) {
                ans[i] = 0;
            } else {
                ans[i] = pairSum(i, lists, signalSpeed);
            }
        }

        return ans;
    }

    /**
     * @Description:
     * 后缀和计算总配对数
     * @param node
     * @param lists
     * @param signalSpeed
     * @return int
     * @author marks
     * @CreateDate: 2025/8/4 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int pairSum(int node, List<int[]>[] lists, int signalSpeed) {
        List<Integer> array = new ArrayList<>();
        for (int[] curr : lists[node]) {
            boolean[] used = new boolean[n];
            used[node] = true;
            int res = dfs(curr[0], curr[1], used, signalSpeed, lists);
            if (res > 0) {
                array.add(res);
            }
        }
        if (array.size() < 2) {
            return 0;
        } else {
            int[] toArray = array.stream().mapToInt(Integer::intValue).toArray();
            int len = toArray.length;
            int[] pairSum = new int[len];
            pairSum[len - 1] = toArray[len - 1];

            for (int i = len - 2; i >= 0; i--) {
                pairSum[i] = pairSum[i + 1] + toArray[i];
            }

            int total = 0;
            for (int i = 0; i < len - 1; i++) {
                total += (toArray[i] * pairSum[i + 1]);
            }

            return total;
        }
    }

    /**
     * @Description:
     * 深度优先搜索计算符合的服务器数量
     * @param root
     * @param preSum
     * @param used
     * @param signalSpeed
     * @param lists
     * @return int
     * @author marks
     * @CreateDate: 2025/8/4 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int dfs(int root, int preSum, boolean[] used, int signalSpeed, List<int[]>[] lists) {
        if (used[root]) {
            return 0;
        }

        int count = (preSum % signalSpeed == 0) ? 1 : 0;
        used[root] = true;

        for (int[] next : lists[root]) {
            int nextRoot = next[0];
            if (!used[nextRoot]) {
                int currSum = preSum + next[1];
                count += dfs(nextRoot, currSum, used, signalSpeed, lists);
            }
        }

        return count;
    }

}


