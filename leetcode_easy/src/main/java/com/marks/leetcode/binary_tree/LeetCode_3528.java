package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/30 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3528 {
    private final int MOD = (int) (1e9 + 7);
    /**
     * @Description:
     * 有 n 种单位，编号从 0 到 n - 1。
     * 给你一个二维整数数组 conversions，长度为 n - 1，
     * 其中 conversions[i] = [sourceUniti, targetUniti, conversionFactori] ，表示一个 sourceUniti 类型的单位等于 conversionFactori 个 targetUniti 类型的单位。
     *
     * 请你返回一个长度为 n 的数组 baseUnitConversion，其中 baseUnitConversion[i] 表示 一个 0 类型单位等于多少个 i 类型单位。
     * 由于结果可能很大，请返回每个 baseUnitConversion[i] 对 10^9 + 7 取模后的值。
     *
     * tips:
     * 2 <= n <= 10^5
     * conversions.length == n - 1
     * 0 <= sourceUniti, targetUniti < n
     * 1 <= conversionFactori <= 10^9
     * 保证单位 0 可以通过 唯一 的转换路径（不需要反向转换）转换为任何其他单位。
     * @param conversions
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/30 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] baseUnitConversions(int[][] conversions) {
        int[] result;
        result = method_01(conversions);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： conversions = [[0,1,2],[0,2,3],[1,3,4],[1,4,5],[2,5,2],[4,6,3],[5,7,4]]
     * 输出： [1,2,3,8,10,6,30,24]
     *
     * AC: 40ms(71.51%)/97.68MB(98.63%)
     * @param conversions 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/7/30 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] conversions) {
        int n = conversions.length;
        int[] ans = new int[n + 1];
        ans[0] = 1;

        List<long[]>[] lists = new List[n + 1];

        for (int i = 0; i < n + 1; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int[] conversion : conversions) {
            int start = conversion[0];
            long end = conversion[1];
            long count = conversion[2];

            lists[start].add(new long[]{end, count});
        }

        Queue<long[]> queue = new LinkedList<>();
        queue.add(new long[]{0, 1});

        while (!queue.isEmpty()) {
            long[] curr = queue.poll();
            long currId = curr[0];
            long preCount = curr[1];

            for (long[] next : lists[(int) currId]) {
                int nextId = (int) next[0];
                int nextCount = (int) ((preCount * next[1]) % MOD);
                ans[nextId] = nextCount;
                queue.add(new long[]{nextId, nextCount});
            }
        }

        return ans;
    }

}
