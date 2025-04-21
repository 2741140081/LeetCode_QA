package com.marks.leetcode.graph_theory_algorithm.dfs;

import com.marks.utils.UnionF;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/18 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2492 {
    /**
     * @Description: [
     * 给你一个正整数 n ，表示总共有 n 个城市，城市从 1 到 n 编号。给你一个二维数组 roads ，其中 roads[i] = [ai, bi, distancei]
     * 表示城市 ai 和 bi 之间有一条 双向 道路，道路距离为 distancei 。城市构成的图不一定是连通的。
     *
     * 两个城市之间一条路径的 分数 定义为这条路径中道路的 最小 距离。
     *
     * 城市 1 和城市 n 之间的所有路径的 最小 分数。
     *
     * 注意：
     *
     * 一条路径指的是两个城市之间的道路序列。
     * 一条路径可以 多次 包含同一条道路，你也可以沿着路径多次到达城市 1 和城市 n 。
     * 测试数据保证城市 1 和城市n 之间 至少 有一条路径。
     * ]
     * @param n 
     * @param roads
     * @return int
     * @author marks
     * @CreateDate: 2024/12/18 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minScore(int n, int[][] roads) {
        int result;
        result = method_01(n, roads);
        return result;
    }

    /**
     * @Description: [
     * 并查集, 判断r[0], r[1] 与 c1的连通性
     *
     * AC:11ms/120.15MB
     * ]
     * @param n
     * @param roads
     * @return int
     * @author marks
     * @CreateDate: 2024/12/18 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] roads) {
        UnionF uf = new UnionF(n);
        for (int[] road : roads) {
            int x = road[0] - 1, y = road[1] - 1;
            uf.union(x, y);
        }
        int ans = Integer.MAX_VALUE;
        for (int[] road : roads) {
            int x = road[0] - 1, y = road[1] - 1, distance = road[2];
            if (x != 0) {
                if (uf.isConnected(0, x)) {
                    ans = Math.min(ans, distance);
                }
            }
            if (y != 0) {
                if (uf.isConnected(0, y)) {
                    ans = Math.min(ans, distance);
                }
            }
        }
        return ans;
    }
}
