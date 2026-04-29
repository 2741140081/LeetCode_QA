package com.marks.leetcode.DP_II;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1494 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/27 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1494 {

    /**
     * @Description:
     * 给你一个整数 n 表示某所大学里课程的数目，编号为 1 到 n ，数组 relations 中， relations[i] = [xi, yi]  表示一个先修课的关系，
     * 也就是课程 xi 必须在课程 yi 之前上。同时你还有一个整数 k 。
     * 在一个学期中，你 最多 可以同时上 k 门课，前提是这些课的先修课在之前的学期里已经上过了。
     * 请你返回上完所有课最少需要多少个学期。题目保证一定存在一种上完所有课的方式。
     *
     * tips:
     * 1 <= n <= 15
     * 1 <= k <= n
     * 0 <= relations.length <= n * (n-1) / 2
     * relations[i].length == 2
     * 1 <= xi, yi <= n
     * xi != yi
     * 所有先修关系都是不同的，也就是说 relations[i] != relations[j] 。
     * 题目输入的图是个有向无环图。
     * @param: n
     * @param: relations
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/27 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        int result;
        result = method_01(n, relations, k);
        result = method_02(n, relations, k);
        return result;
    }

    /**
     * @Description:
     * 直接 CV 官方题解吧, 不会写 need todo
     * @param: n
     * @param: relations
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/27 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[][] relations, int k) {
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        int[] need = new int[1 << n];
        for (int[] edge : relations) {
            need[(1 << (edge[1] - 1))] |= 1 << (edge[0] - 1);
        }
        dp[0] = 0;
        for (int i = 1; i < (1 << n); ++i) {
            need[i] = need[i & (i - 1)] | need[i & (-i)];
            if ((need[i] | i) != i) { // i 中有任意一门课程的前置课程没有完成学习
                continue;
            }
            int valid = i ^ need[i]; // 当前学期可以进行学习的课程集合
            if (Integer.bitCount(valid) <= k) { // 如果个数小于 k，则可以全部学习，不再枚举子集
                dp[i] = Math.min(dp[i], dp[i ^ valid] + 1);
            } else { // 否则枚举当前学期需要进行学习的课程集合
                for (int sub = valid; sub > 0; sub = (sub - 1) & valid) {
                    if (Integer.bitCount(sub) <= k) {
                        dp[i] = Math.min(dp[i], dp[i ^ sub] + 1);
                    }
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    /**
     * @Description:
     * 1. 主要处理可选课程大于 k 时, 如何选择课程?, 需要在 t 个元素中选择 k 个元素, 使得课程所需学期最小
     * 2. 假设有 t 门课程可选, 怎么得到所有的组合?
     * @param: n
     * @param: relations
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/27 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] relations, int k) {
        if (k == 1) {
            return n;
        }
        // 构建邻接表
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 入度表, 入度为0的课程可以选
        int[] inDegree = new int[n];
        for (int[] relation : relations) {
            graph[relation[0] - 1].add(relation[1] - 1);
            inDegree[relation[1] - 1]++;
        }

        return 0;
    }

}
