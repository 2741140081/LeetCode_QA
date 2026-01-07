package com.marks.leetcode.graph_theory;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2285 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 11:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2285 {

    /**
     * @Description:
     * 给你一个整数 n ，表示一个国家里的城市数目。城市编号为 0 到 n - 1 。
     * 给你一个二维整数数组 roads ，其中 roads[i] = [ai, bi] 表示城市 ai 和 bi 之间有一条 双向 道路。
     * 你需要给每个城市安排一个从 1 到 n 之间的整数值，且每个值只能被使用 一次 。道路的 重要性 定义为这条道路连接的两座城市数值 之和 。
     * 请你返回在最优安排下，所有道路重要性 之和 最大 为多少。
     * @param: n
     * @param: roads
     * @return long
     * @author marks
     * @CreateDate: 2026/01/07 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumImportance(int n, int[][] roads) {
        long result;
        result = method_01(n, roads);
        return result;
    }

    /**
     * @Description:
     * 1. 构建入度表，并求和
     * AC: 11ms/114.6MB
     * @param: n
     * @param: roads
     * @return long
     * @author marks
     * @CreateDate: 2026/01/07 11:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[][] roads) {
        int[] inDegree = new int[n];
        for (int[] road : roads) {
            inDegree[road[0]]++;
            inDegree[road[1]]++;
        }
        // 对入度表排序, 降序排序
        Arrays.sort(inDegree);
        long ans = 0;
        long index = n;
        for (int i = n - 1; i >= 0; i--) {
            ans += (index-- * inDegree[i]);
        }
        return ans;
    }

}
