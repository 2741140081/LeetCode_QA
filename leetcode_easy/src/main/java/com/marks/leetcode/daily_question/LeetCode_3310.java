package com.marks.leetcode.daily_question;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3310 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/5 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3310 {

    /**
     * @Description:
     * 你正在维护一个项目，该项目有 n 个方法，编号从 0 到 n - 1。
     * 给你两个整数 n 和 k，以及一个二维整数数组 invocations，其中 invocations[i] = [ai, bi] 表示方法 ai 调用了方法 bi。
     * 已知如果方法 k 存在一个已知的 bug。
     * 那么方法 k 以及它直接或间接调用的任何方法都被视为 可疑方法 ，我们需要从项目中移除这些方法。
     * 只有当一组方法没有被这组之外的任何方法调用时，这组方法才能被移除。
     * 返回一个数组，包含移除所有 可疑方法 后剩下的所有方法。你可以以任意顺序返回答案。
     * 如果无法移除 所有 可疑方法，则 不 移除任何方法。
     *
     * tips:
     * 1 <= n <= 10^5
     * 0 <= k <= n - 1
     * 0 <= invocations.length <= 2 * 10^5
     * invocations[i] == [ai, bi]
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * invocations[i] != invocations[j]
     * @param: n
     * @param: k
     * @param: invocations
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/08/05 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer> result;
        result = method_01(n, k, invocations);
        return result;
    }

    /**
     * @Description:
     * 1. 可疑方法判断: [a, b] 调用关系, 即方法 a 调用 方法 b, 并且方法a 是可疑方法, 那么方法 b 也是可疑方法,
     * 相当于从方法 a 来进行追根溯源, 找到所有可疑方法.
     * 2. 入度表, inDegree 用来记录节点的入度情况, [a, b], 则 inDegree[b]++, 并且仅当 inDegree[i] == 0 时, 可疑方法 i 才允许被移除,
     * 否则不能移除
     * 3. 使用广度优先搜索, 从节点 k 出发, 模拟删除 k 节点并且减少 k 所调用方法节点的入度值, 并且将 可疑方法添加到待删除队列
     * AC: 59ms/273.9MB
     * @param: n
     * @param: k
     * @param: invocations
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/08/05 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int n, int k, int[][] invocations) {
        // 将 invocations 转换成邻接表
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 构建入度表
        int[] inDegree = new int[n];
        // 遍历 invocations, 构建 graph
        for (int[] invocation : invocations) {
            int u = invocation[0];
            int v = invocation[1];
            inDegree[v]++;
            graph[u].add(v);
        }
        // 创建队列
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(k);
        boolean[] visited = new boolean[n];
        visited[k] = true;
        while (!queue.isEmpty()) {
            int curr = queue.pop();
            for (Integer next : graph[curr]) {
                inDegree[next]--;
                if (!visited[next]) {
                    queue.offer(next);
                    visited[next] = true;
                }
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (visited[i] && inDegree[i] != 0) {
                // 当前节点是可疑节点, 并且无法移除
                ans.clear();
                for (int j = 0; j < n; j++) {
                    ans.add(j);
                }
                return ans;
            } else if (!visited[i]) {
                // 当前不是可疑方法
                ans.add(i);
            }
        }

        return ans;
    }

}
