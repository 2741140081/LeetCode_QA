package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/11 15:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2440 {

    /**
     * @Description:
     * 有一棵 n 个节点的无向树，节点编号为 0 到 n - 1 。
     * 给你一个长度为 n 下标从 0 开始的整数数组 nums ，其中 nums[i] 表示第 i 个节点的值。
     * 同时给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示节点 ai 与 bi 之间有一条边。
     *
     * 你可以 删除 一些边，将这棵树分成几个连通块。一个连通块的 价值 定义为这个连通块中 所有 节点 i 对应的 nums[i] 之和。
     * 你需要删除一些边，删除后得到的各个连通块的价值都相等。
     * 请返回你可以删除的边数 最多 为多少。
     *
     * tips:
     * 1 <= n <= 2 * 10^4
     * nums.length == n
     * 1 <= nums[i] <= 50
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= edges[i][0], edges[i][1] <= n - 1
     * edges 表示一棵合法的树。
     * @param nums
     * @param edges
     * @return int
     * @author marks
     * @CreateDate: 2025/8/11 15:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int componentValue(int[] nums, int[][] edges) {
        int result;
        result = method_01(nums, edges);
        return result;
    }

    private int ans;
    /**
     * @Description:
     * E1:
     * 输入：nums = [6,2,2,2,6], edges = [[0,1],[1,2],[1,3],[3,4]]
     * 输出：2
     * 1. 对于连通量之和的最小值 min = Max(nums[i]), 最大值 max = Sum(nums[i]) / 2
     * 2. 找到对应的最大值节点 x, 判断 x 的值是否满足将 无向树分割成 n个连通块，且每个连通块的节点值之和等于 x 的值
     * 3. 如果不满足, 遍历x的所有子节点, 分别是s1, s2, s3, ..., 找到一个最小的节点 sMin
     * 4. 通过 dfs 遍历数, 需要记录下来, 但是该从那个节点开始? 是从最大值的节点 x 还是 根据习惯从 节点0 开始?
     * 5. 我是先通过 min 和 max 找到所有可能的分割数, 还是 先通过 dfs 找到所有可能的分割点?
     * 6. 决定了, 先找到所有可能的分割数, 然后再通过 dfs 判断分割数是否符合要求
     *
     * AC: 132ms(18.18%)/56.90MB(72.73%)
     * @param nums
     * @param edges
     * @return int
     * @author marks
     * @CreateDate: 2025/8/11 15:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int[][] edges) {
        int n = edges.length + 1;
        if (n == 1) {
            return 0;
        }

        List<Integer>[] graph = buildGraph(edges);
        int min = Arrays.stream(nums).max().getAsInt();
        long sum = Arrays.stream(nums).sum();

        List<Integer> lists = new ArrayList<>();
        for (int i = min; i <= sum / 2; i++) {
            if (sum % i == 0) {
                lists.add(i);
            }
        }
        for (int i = 0; i < lists.size(); i++) {
            ans = 0;
            int k = lists.get(i);
            dfs(0, -1, graph, nums, k);

            if ((long) ans * k == sum) {
                return ans - 1;
            }
        }

        return 0;
    }


    private long dfs(int node, int parent, List<Integer>[] graph, int[] values, int k) {
        long sum = values[node];

        for (int next : graph[node]) {
            if (next != parent) {
                long childValue = dfs(next, node, graph, values, k);
                sum += childValue;
            }
        }

        if (sum % k == 0 ) {
            ans++;
        }
        return sum % k;
    }

    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        return graph;
    }
}
