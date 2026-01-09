package com.marks.leetcode.graph_theory;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2374 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 15:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2374 {

    /**
     * @Description:
     * 给你一个有向图，图中有 n 个节点，节点编号从 0 到 n - 1 ，其中每个节点都 恰有一条 出边。
     * 图由一个下标从 0 开始、长度为 n 的整数数组 edges 表示，其中 edges[i] 表示存在一条从节点 i 到节点 edges[i] 的 有向 边。
     *
     * 节点 i 的 边积分 定义为：所有存在一条指向节点 i 的边的节点的 编号 总和。
     * 返回 边积分 最高的节点。如果多个节点的 边积分 相同，返回编号 最小 的那个。
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int edgeScore(int[] edges) {
        int result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * 需要注意溢出风险
     * AC: 3ms/101.24MB
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 15:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] edges) {
        // 入度表
        int n = edges.length;
        long[] inDegree = new long[n]; // 记录入度和
        for (int i = 0; i < n; i++) {
            inDegree[edges[i]] += i; // 溢出了, 需要用long
        }
        long max = inDegree[0];
        int ans = 0;
        for (int i = 1; i < n; i++) {
            if (max < inDegree[i]) {
                max = inDegree[i];
                ans = i;
            }
        }
        return ans;
    }

}
