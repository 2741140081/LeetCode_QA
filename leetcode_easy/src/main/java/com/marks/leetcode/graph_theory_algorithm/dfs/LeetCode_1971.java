package com.marks.leetcode.graph_theory_algorithm.dfs;

import com.marks.utils.UnionF;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1971 {
    /**
     * @Description: [功能描述]
     * @param n 
     * @param edges 
     * @param source 
     * @param destination 
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/17 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        boolean result;
        result = method_01(n, edges, source, destination);
        return result;
    }

    /**
     * @Description: [
     * 我想用并查集来解决这题
     * AC:15ms/170.23MB
     * ]
     * @param n
     * @param edges
     * @param source
     * @param destination
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/17 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n, int[][] edges, int source, int destination) {
        UnionF uf = new UnionF(n);
        for (int i = 0; i < edges.length; i++) {
            uf.union(edges[i][0], edges[i][1]);
        }
        return uf.isConnected(source, destination);
    }
}
