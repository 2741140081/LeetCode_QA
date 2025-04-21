package com.marks.leetcode.graph_theory_algorithm.dfs;

import com.marks.utils.UnionF;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/18 15:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1319 {
    /**
     * @Description: [
     * 用以太网线缆将 n 台计算机连接成一个网络，计算机的编号从 0 到 n-1。
     * 线缆用 connections 表示，其中 connections[i] = [a, b] 连接了计算机 a 和 b。
     * 网络中的任何一台计算机都可以通过网络直接或者间接访问同一个网络中其他任意一台计算机。
     *
     * 给你这个计算机网络的初始布线 connections，你可以拔开任意两台直连计算机之间的线缆，并用它连接一对未直连的计算机。
     * 请你计算并返回使所有计算机都连通所需的最少操作次数。如果不可能，则返回 -1 。
     * ]
     * @param n 
     * @param connections
     * @return int
     * @author marks
     * @CreateDate: 2024/12/18 15:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int makeConnected(int n, int[][] connections) {
        int result;
        result = method_01(n, connections);
        return result;
    }

    /**
     * @Description: [
     * 可以用并查集,
     * 1. 对connections[i] 进行遍历,
     * 2. 判断conn[i][0], conn[i][1] 是否isConnected, true: pre[uf.find(x)]++, false: uf.union(x, y)
     * 3. 遍历结束, uf.getCount() <= pre[].sum, return count - 1, else return -1
     *
     * AC:9ms/59.33MB
     * ]
     * @param n
     * @param connections
     * @return int
     * @author marks
     * @CreateDate: 2024/12/18 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] connections) {
        UnionF uf = new UnionF(n);
        int[] pre = new int[n]; // 存储节点多余的连接线数量
        for (int[] item : connections) {
            int x = item[0], y = item[1];
            if (uf.isConnected(x, y)) {
                pre[uf.find(x)]++;
            }
            uf.union(x, y);

        }
        int sum = Arrays.stream(pre).sum();
        int count = uf.getCount() - 1;
        return count <= sum ? count : -1;
    }
}
