package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/27 16:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_851 {
    /**
     * @Description: [
     * <p>有一组 n 个人作为实验对象，从 0 到 n - 1 编号，其中每个人都有不同数目的钱，以及不同程度的安静值（quietness）。为了方便起见，我们将编号为 x 的人简称为 "person x "。
     * <p>给你一个数组 richer ，其中 richer[i] = [ai, bi] 表示 person ai 比 person bi 更有钱。另给你一个整数数组 quiet ，
     * <p>其中 quiet[i] 是 person i 的安静值。richer 中所给出的数据 逻辑自洽（也就是说，在 person x 比 person y 更有钱的同时，不会出现 person y 比 person x 更有钱的情况 ）。
     * <p>现在，返回一个整数数组 answer 作为答案，其中 answer[x] = y 的前提是，在所有拥有的钱肯定不少于 person x 的人中，person y 是最不安静的人（也就是安静值 quiet[y] 最小的人）。
     * <p>tips:
     * n == quiet.length
     * 1 <= n <= 500
     * 0 <= quiet[i] < n
     * quiet 的所有值 互不相同
     * 0 <= richer.length <= n * (n - 1) / 2
     * 0 <= ai, bi < n
     * ai != bi
     * richer 中的所有数对 互不相同
     * 对 richer 的观察在逻辑上是一致的
     * ]
     * @param richer 
     * @param quiet 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/27 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int[] result;
        result = method_01(richer, quiet);
        return result;
    }

    /**
     * @Description: [
     * <p> 1. richer[i] = [ai, bi] 表示 person ai 比 person bi 更有钱
     * <p> 2. 这种感觉不太行, 还是直接暴力把全部祖先给找出来
     * 暴力复杂度难以想象
     * AC:319ms/56.68MB
     *
     * 查看官解优化以下ans, 不需要求所有节点的祖先节点
     *
     * AC:7ms/53.56MB
     * ]
     * @param richer
     * @param quiet
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/27 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] richer, int[] quiet) {
        int n = quiet.length;
        List<Integer>[] lists = new ArrayList[n];
        int[] ans = new int[n];
        int[] inDegree = new int[n];
        Arrays.fill(inDegree, 0);
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
            ans[i] = i;
        }


        for (int[] array : richer) {
            int start = array[0];
            int end = array[1];
            inDegree[end]++;
            lists[start].add(end);
        }

        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                ans[i] = i;
            }
        }

        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            for (int j : lists[curr_i]) {
                if (quiet[ans[j]] > quiet[ans[curr_i]]) {
                    ans[j] = ans[curr_i];
                }
                inDegree[j]--;
                if (inDegree[j] == 0) {
                    queue.offer(j);
                }
            }
        }
        return ans;
    }
}
