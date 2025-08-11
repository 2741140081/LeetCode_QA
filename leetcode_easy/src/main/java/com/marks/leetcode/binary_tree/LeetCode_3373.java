package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/5 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3373 {
    
    
    /**
     * @Description:
     * 有两棵 无向 树，分别有 n 和 m 个树节点。两棵树中的节点编号分别为[0, n - 1] 和 [0, m - 1] 中的整数。
     * 给你两个二维整数 edges1 和 edges2 ，长度分别为 n - 1 和 m - 1 ，
     * 其中 edges1[i] = [ai, bi] 表示第一棵树中节点 ai 和 bi 之间有一条边，edges2[i] = [ui, vi] 表示第二棵树中节点 ui 和 vi 之间有一条边。
     *
     * 如果节点 u 和节点 v 之间路径的边数是偶数，那么我们称节点 u 是节点 v 的 目标节点 。注意 ，一个节点一定是它自己的 目标节点 。
     * 请你返回一个长度为 n 的整数数组 answer ，answer[i] 表示将第一棵树中的一个节点与第二棵树中的一个节点连接一条边后，第一棵树中节点 i 的 目标节点 数目的 最大值 。
     *
     * 注意 ，每个查询相互独立。意味着进行下一次查询之前，你需要先把刚添加的边给删掉。
     * @param edges1 
     * @param edges2
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/5 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int[] result;
        result = method_01(edges1, edges2);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges1 = [[0,1],[0,2],[2,3],[2,4]], edges2 = [[0,1],[0,2],[0,3],[2,7],[1,4],[4,5],[4,6]]
     * 输出：[8,7,7,8,8]
     * 1. 使用染色法处理两棵树
     * 2. 对于第二棵树，通过染色法得到两种颜色的节点数，取较大值作为连接后能增加的目标节点数
     * 3. 对于第一棵树，通过染色法得到每个节点与根节点的奇偶关系，从而计算每个节点的目标节点数
     * 4. 时间复杂度为O(M+N)
     *
     * AC: 163ms(16.53%)/126.87MB(12.39%)
     * @param edges1 
     * @param edges2 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/5 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges1, int[][] edges2) {
        // 构建邻接表
        List<Integer>[] lists1 = buildGraph(edges1);
        List<Integer>[] lists2 = buildGraph(edges2);

        int n = lists1.length;
        int m = lists2.length;

        // 对第二棵树进行染色，计算两种颜色的节点数
        int[] colors2 = new int[m];
        dfsColor(0, -1, 0, lists2, colors2);
        int color2Count0 = 0;
        for (int i = 0; i < m; i++) {
            if (colors2[i] == 0) {
                color2Count0++;
            }
        }
        int maxOdd2 = Math.max(color2Count0, m - color2Count0);

        // 对第一棵树进行染色，计算每个节点的目标节点数
        int[] colors1 = new int[n];
        dfsColor(0, -1, 0, lists1, colors1);
        
        // 计算根节点的目标节点数（颜色为0的节点数）
        int color1Count0 = 0;
        for (int i = 0; i < n; i++) {
            if (colors1[i] == 0) {
                color1Count0++;
            }
        }

        // 结果数组：每个节点的目标节点数 = 与其颜色相同的节点数 + 第二棵树能提供的最大奇数节点数
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            // 与节点i颜色相同的节点数就是目标节点数（在第一棵树内）
            int sameColorCount = (colors1[i] == 0) ? color1Count0 : (n - color1Count0);
            ans[i] = sameColorCount + maxOdd2;
        }

        return ans;
    }

    // 构建邻接表的通用方法
    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] lists = new List[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int[] ed : edges) {
            lists[ed[0]].add(ed[1]);
            lists[ed[1]].add(ed[0]);
        }
        return lists;
    }

    // 染色法：对树进行二分图染色
    private void dfsColor(int node, int parent, int color, List<Integer>[] lists, int[] colors) {
        colors[node] = color;
        
        for (int child : lists[node]) {
            if (child != parent) {
                dfsColor(child, node, 1 - color, lists, colors);
            }
        }
    }
}
