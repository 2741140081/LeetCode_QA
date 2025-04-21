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
 * @date 2024/12/20 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2685 {
    private int[] pre;

    /**
     * @Description: [
     * 给你一个整数 n 。现有一个包含 n 个顶点的 无向 图，顶点按从 0 到 n - 1 编号。给你一个二维整数数组 edges 其中 edges[i] = [ai, bi] 表示顶点 ai 和 bi 之间存在一条 无向 边。
     * <p>返回图中 完全连通分量 的数量。
     * <p>如果在子图中任意两个顶点之间都存在路径，并且子图中没有任何一个顶点与子图外部的顶点共享边，则称其为 连通分量 。
     * <p>如果连通分量中每对节点之间都存在一条边，则称其为 完全连通分量 。
     * ]
     * @param n 
     * @param edges
     * @return int
     * @author marks
     * @CreateDate: 2024/12/20 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countCompleteComponents(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 因为n的最大值是50, 所以如果我boolean[][] array = new boolean[n][n], 默认值false, 如果存在连接,
     * 例如[0, 2], 那么array[0][2] = true, array[2][0] = true
     * <p> AC:15ms/44.26MB
     * @param n
     * @param edges
     * @return int
     * @author marks
     * @CreateDate: 2024/12/20 15:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        pre = new int[n]; // 存储标记
        boolean[][] array = new boolean[n][n];
        for (int[] ints : edges) {
            int x = ints[0], y = ints[1];
            array[x][y] = true;
            array[y][x] = true;
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (pre[i] == 0) {
                List<Integer> list = new ArrayList<>();
                DFSMaxArea(i, array, list);
                if (list.size() <= 2) {
                    ans++;
                } else {
                    if (validateIsCompleteComponent(list, array)) {
                        ans++;
                    }
                }
            }
        }

        return ans;
    }

    private boolean validateIsCompleteComponent(List<Integer> list, boolean[][] array) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (!array[list.get(i)][list.get(j)]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void DFSMaxArea(int i, boolean[][] array, List<Integer> list) {
        if (pre[i] == 1) {
            return;
        }
        pre[i] = 1;
        list.add(i);
        for (int j = 0; j < array[i].length; j++) {
            if (array[i][j]) {
                DFSMaxArea(j, array, list);
            }
        }
    }
}
