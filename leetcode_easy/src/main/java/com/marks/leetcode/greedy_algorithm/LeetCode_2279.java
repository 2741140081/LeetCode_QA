package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 17:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2279 {
    
    /**
     * @Description:
     * 现有编号从 0 到 n - 1 的 n 个背包。
     * 给你两个下标从 0 开始的整数数组 capacity 和 rocks 。
     * 第 i 个背包最大可以装 capacity[i] 块石头，当前已经装了 rocks[i] 块石头。
     * 另给你一个整数 additionalRocks ，表示你可以放置的额外石头数量，石头可以往 任意 背包中放置。
     *
     * 请你将额外的石头放入一些背包中，并返回放置后装满石头的背包的 最大 数量。
     * @param capacity 
     * @param rocks 
     * @param additionalRocks
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {
        int result;
        result = method_01(capacity, rocks, additionalRocks);
        return result;
    }

    /**
     * @Description:
     * AC: 15ms/55.83MB
     * @param capacity 
     * @param rocks 
     * @param additionalRocks 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] capacity, int[] rocks, int additionalRocks) {
        int n = capacity.length;
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = capacity[i] - rocks[i];
        }
        Arrays.sort(diff);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (additionalRocks > 0) {
                additionalRocks -= diff[i];
                diff[i] = additionalRocks >= 0 ? 0 : -1;
            }
            if (diff[i] == 0) {
                ans++;
            }
        }
        return ans;
    }
}
