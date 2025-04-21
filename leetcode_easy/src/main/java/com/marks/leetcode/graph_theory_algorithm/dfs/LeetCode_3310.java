package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/18 16:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3310 {
    private int[] pre;
    /**
     * @Description: [
     * 你正在维护一个项目，该项目有 n 个方法，编号从 0 到 n - 1。
     * 给你两个整数 n 和 k，以及一个二维整数数组 invocations，其中 invocations[i] = [ai, bi] 表示方法 ai 调用了方法 bi。
     * 已知如果方法 k 存在一个已知的 bug。那么方法 k 以及它直接或间接调用的任何方法都被视为 可疑方法 ，我们需要从项目中移除这些方法。
     * 只有当一组方法没有被这组之外的任何方法调用时，这组方法才能被移除。
     * 返回一个数组，包含移除所有 可疑方法 后剩下的所有方法。你可以以任意顺序返回答案。如果无法移除 所有 可疑方法，则 不 移除任何方法。
     *
     * tips:
     * 1 <= n <= 10^5
     * 0 <= k <= n - 1
     * 0 <= invocations.length <= 2 * 10^5
     * invocations[i] == [ai, bi]
     * 0 <= ai, bi <= n - 1
     * ai != bi
     * invocations[i] != invocations[j]
     * ]
     * @param n
     * @param k
     * @param invocations
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/18 16:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer> result;
        result = method_01(n, k, invocations);
        return result;
    }

    /**
     * @Description: [
     * 1. invocations[i] = [ai, bi] 表示方法 ai 调用了方法 bi。 ai -> bi, 如果bi 被删除, 那么ai 报错, 如果ai 删除, bi 无影响
     * <p>2. invocations[][] 数据进行分层存储, 第 i 层 存储 m[i] 调用的方法, 方便进行 DFS 操作
     * <p>3. 可能存在环, a0 -> a1; a1 -> a2; a2 -> a0; a0, a1, a2成环, 添加额外数组记录状态.
     *
     * 超时!!! 691/775
     *
     * <p>看了下题解, 感觉是并查集的复杂度有问题, 需要修改
     * 超时!!! 691/775
     *
     * <p>哎! 还是超时, 再去看看题解, 思路是一样的啊, 可能是我用的是List<List<Integer>> 存储二维数组
     * 而题解是List<Integer>[] g = new ArrayList[n];
     *
     * <p>找到了, 这一步的导致超时!!! 这个操作的时间复杂度是O(n^2), 因为total.remove(num) 是线性的, 时间复杂度是O(n)
     * ans的长度近似于O(n), 所以总的时间复杂度O(n^2), 超时!!!
     * for (Integer num : ans) {
     *    total.remove(num);
     * }
     *
     * AC:74ms/114.70MB
     *
     * ]
     * @param n
     * @param k
     * @param invocations
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/18 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int n, int k, int[][] invocations) {
        pre = new int[n];
        List<Integer>[] list = new ArrayList[n];
        List<Integer> total = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
            total.add(i);
        }

        for (int[] num : invocations) {
            int x = num[0], y = num[1];
            list[x].add(y);;
        }
        DFSMaxArea(k, list);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < pre.length; i++) {
            if (pre[i] != 1) {
                // 非可疑的方法
                ans.add(i);
            }
        }

        for (int[] num : invocations) {
            int x = num[0], y = num[1];
            if (pre[x] == 0 && pre[y] == 1) {
                // 存在非可疑方法 调用 可疑方法
                return total;
            }
        }
        return ans;
    }

    private void DFSMaxArea(int i, List<Integer>[] list) {
        if (pre[i] != 0) {
            return;
        }
        pre[i] = 1; // 标记
        for (Integer num : list[i]) {
            DFSMaxArea(num, list);
        }
    }
}
