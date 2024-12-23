package com.marks.leetcode.graph_theory_algorithm.dfs;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/23 11:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_924 {
    private int[] pre;
    /**
     * @Description: [
     * <p>给出了一个由 n 个节点组成的网络，用 n × n 个邻接矩阵图 graph 表示。在节点网络中，当 graph[i][j] = 1 时，表示节点 i 能够直接连接到另一个节点 j。
     * <p>一些节点 initial 最初被恶意软件感染。只要两个节点直接连接，且其中至少一个节点受到恶意软件的感染，那么两个节点都将被恶意软件感染。这种恶意软件的传播将继续，直到没有更多的节点可以被这种方式感染。
     * <p>假设 M(initial) 是在恶意软件停止传播之后，整个网络中感染恶意软件的最终节点数。
     * <p>如果从 initial 中移除某一节点能够最小化 M(initial)， 返回该节点。如果有多个节点满足条件，就返回索引最小的节点。
     * <p>请注意，如果某个节点已从受感染节点的列表 initial 中删除，它以后仍有可能因恶意软件传播而受到感染。
     *
     * <p>tips:
     * n == graph.length
     * n == graph[i].length
     * 2 <= n <= 300
     * graph[i][j] == 0 或 1.
     * graph[i][j] == graph[j][i]
     * graph[i][i] == 1
     * 1 <= initial.length <= n
     * 0 <= initial[i] <= n - 1
     * initial 中所有整数均不重复
     * ]
     * @param graph 
     * @param initial
     * @return int
     * @author marks
     * @CreateDate: 2024/12/23 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMalwareSpread(int[][] graph, int[] initial) {
        int result;
        result = method_01(graph, initial);
        return result;
    }

    /**
     * @Description: [
     * <p>这是一个无向图, 算了直接暴力试试看, 整体遍历initial[], i = initial[0 ~ n - 1],
     * <p>删除i, 相当于不遍历graph[i] 行, 然后计算其他initial[]对graph[]的影响, 并且求出最终感染数count,
     * <p>如果 min > count_i, 那么min = Math.min(min, count), index = i;
     * <p> min = count_i, index = Math.min(index, i); other condition is continue;
     *
     * AC:61ms/61.08MB
     * ]
     * @param graph
     * @param initial
     * @return int
     * @author marks
     * @CreateDate: 2024/12/23 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] graph, int[] initial) {
        int n = graph.length;
        if (initial.length == 1) {
            return initial[0];
        }
        int min = n;
        int index = 0;
        for (int i = 0; i< initial.length; i++) {
            pre = new int[n];
            for (int j = 0; j < initial.length; j++) {
                if(i == j){
                    continue;
                }
                DFSMaxArea(graph, initial[j]);
            }
            int sum = Arrays.stream(pre).sum();
            if (min > sum) {
                min = sum;
                index = i;
            } else if (min == sum) {
                if (initial[index] > initial[i]) {
                    index = i;
                }
            }
        }
        return initial[index];
    }

    private void DFSMaxArea(int[][] graph, int i) {
        if (pre[i] == 1) {
            return;
        }
        pre[i] = 1;
        for (int j = 0; j < graph[i].length; j++) {
            if (graph[i][j] == 1 && pre[j] == 0) {
                DFSMaxArea(graph, j);
            }
        }
    }
}
