package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2497 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 9:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2497 {

    /**
     * @Description:
     * 给你一个 n 个点的无向图，节点从 0 到 n - 1 编号。
     * 给你一个长度为 n 下标从 0 开始的整数数组 vals ，其中 vals[i] 表示第 i 个节点的值。
     * 同时给你一个二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示节点 ai 和 bi 之间有一条双向边。
     * 星图 是给定图中的一个子图，它包含一个中心节点和 0 个或更多个邻居。换言之，星图是给定图中一个边的子集，且这些边都有一个公共节点。
     * 下图分别展示了有 3 个和 4 个邻居的星图，蓝色节点为中心节点。
     * 星和 定义为星图中所有节点值的和。
     * 给你一个整数 k ，请你返回 至多 包含 k 条边的星图中的 最大星和 。
     * @param: vals
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxStarSum(int[] vals, int[][] edges, int k) {
        int result;
        result = method_01(vals, edges, k);
        return result;
    }

    /**
     * @Description:
     * 1. 星图是给定图的一个子集, 假设有节点 i, i 包含有 x 个邻居 x >= k, 那么 i 的最大星河为 vals[i] + Math.max(vals[j] for j in i's neighbors)
     * 求解数组 List<Integer> neighbors, 中取 k 个元素, 求和, 根据贪心的策略, 取前 k 个值, 需要进行排序操作
     * 2. 用 outDegree[] 记录每个节点的出度, 对 outDegree[i] >= k 的元素, 获取它的邻居(已提前构建邻接表), 然后排序 获取前 k 个值, 然后求和
     * 3. 存在问题, 因为题目要求的是至多 k 条边, 也就是可以 可以取[0~k] 条边, 修改代码
     * AC: 78ms/134.59MB
     * @param: vals
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] vals, int[][] edges, int k) {
        int n = vals.length;
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 构建出度表
        int[] outDegree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            graph[x].add(y);
            graph[y].add(x);
            outDegree[x]++;
            outDegree[y]++;
        }
        // 遍历所有节点
        int ans = Integer.MIN_VALUE; // 最大星和
        for (int i = 0; i < n; i++) {
            List<Integer> nValues = new ArrayList<>(); // 节点 i 的邻居的值
            for (int j : graph[i]) {
                nValues.add(vals[j]);
            }
            Collections.sort(nValues);
            int sum = vals[i];
            if (outDegree[i] >= k) {
                for (int j = nValues.size() - 1; j >= nValues.size() - k; j--) {
                    if (nValues.get(j) > 0) {
                        sum += nValues.get(j);
                    } else {
                        break;
                    }
                }

            } else {
                for (int j = nValues.size() - 1; j >= 0; j--) {
                    if (nValues.get(j) > 0) {
                        sum += nValues.get(j);
                    } else {
                        break;
                    }
                }
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

}
