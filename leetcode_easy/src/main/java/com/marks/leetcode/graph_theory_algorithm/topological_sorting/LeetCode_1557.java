package com.marks.leetcode.graph_theory_algorithm.topological_sorting;

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
 * @date 2024/12/24 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1557 {
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        List<Integer> result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description: [
     * 我需要找到某一个节点的祖先节点, 即该节点的入度为0
     * AC:12ms/81.01MB
     * ]
     * @param n
     * @param edges
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2024/12/24 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int n, List<List<Integer>> edges) {
        int[] inDegree = new int[n];
        List<Integer> ans = new ArrayList<>();
        Arrays.fill(inDegree, 0);
        for (List<Integer> edge : edges) {
            int start = edge.get(0);
            int end = edge.get(1);
            inDegree[end]++;
        }

        // BFS, 起始点为pre[i] == 0, inDegree[i] == 0, 即入度为0, 且未遍历的节点
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                ans.add(i);
            }
        }
        return ans;
    }
}
