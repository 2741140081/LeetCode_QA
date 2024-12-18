package com.marks.leetcode.graph_theory_algorithm.dfs;

import com.marks.utils.UnionF;

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
    /**
     * @Description: [
     * 你正在维护一个项目，该项目有 n 个方法，编号从 0 到 n - 1。
     * 给你两个整数 n 和 k，以及一个二维整数数组 invocations，其中 invocations[i] = [ai, bi] 表示方法 ai 调用了方法 bi。
     * 已知如果方法 k 存在一个已知的 bug。那么方法 k 以及它直接或间接调用的任何方法都被视为 可疑方法 ，我们需要从项目中移除这些方法。
     * 只有当一组方法没有被这组之外的任何方法调用时，这组方法才能被移除。
     * 返回一个数组，包含移除所有 可疑方法 后剩下的所有方法。你可以以任意顺序返回答案。如果无法移除 所有 可疑方法，则 不 移除任何方法。
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
     * 1.
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
        UnionF uf = new UnionF(n);
        for (int[] item : invocations) {
            int x = item[0], y = item[1];
            uf.union(x, y);
        }
        // TODO
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ans.add(i);
        }
        boolean flag = true;
        for (int i = 0; i < n; i++) {
            if (i == k) {
                continue;
            }
            if (uf.isConnected(k, i)) {

            }
        }
        return ans;
    }
}
