package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3160 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/12 16:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3160 {

    /**
     * @Description:
     * 给你一个整数 limit 和一个大小为 n x 2 的二维数组 queries 。
     * 总共有 limit + 1 个球，每个球的编号为 [0, limit] 中一个 互不相同 的数字。
     * 一开始，所有球都没有颜色。queries 中每次操作的格式为 [x, y] ，你需要将球 x 染上颜色 y 。
     * 每次操作之后，你需要求出所有球颜色的数目。
     * 请你返回一个长度为 n 的数组 result ，其中 result[i] 是第 i 次操作以后颜色的数目。
     * 注意 ，没有染色的球不算作一种颜色。
     * tips:
     * 1 <= limit <= 10^9
     * 1 <= n == queries.length <= 10^5
     * queries[i].length == 2
     * 0 <= queries[i][0] <= limit
     * 1 <= queries[i][1] <= 10^9
     * @param: limit
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/12 16:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] queryResults(int limit, int[][] queries) {
        int[] result;
        result = method_01(limit, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 需要记录每一个球的颜色, 默认颜色为0
     * 2. 由于数据范围较大, 需要使用map 离散化存储而不是使用数组存储球的颜色
     * AC: 43ms/179.63MB
     * @param: limit
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/12 16:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int limit, int[][] queries) {
        int n = queries.length;
        Map<Integer, Integer> cnt = new HashMap<>();
        Map<Integer, Integer> color = new HashMap<>();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int x = queries[i][0]; // 球的编号
            int y = queries[i][1]; // 球的颜色
            // 判断 color 中是否已经存在 x, 即球 x 是否已经染色
            if (color.containsKey(x)) {
                int oldColor = color.get(x);
                cnt.put(oldColor, cnt.getOrDefault(oldColor, 0) - 1);
                if (cnt.getOrDefault(oldColor, 0) == 0) {
                    cnt.remove(oldColor);
                }
            }
            color.put(x, y);
            cnt.put(y, cnt.getOrDefault(y, 0) + 1);
            ans[i] = cnt.size();
        }
        return ans;
    }

}
