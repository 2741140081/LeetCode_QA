package com.marks.leetcode.data_structure.difference;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 16:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1854 {
    /**
     * @Description:
     * 给你一个二维整数数组 logs ，其中每个 logs[i] = [birthi, deathi] 表示第 i 个人的出生和死亡年份。
     * 年份 x 的 人口 定义为这一年期间活着的人的数目。第 i 个人被计入年份 x 的人口需要满足：x 在闭区间 [birthi, deathi - 1] 内。注意，人不应当计入他们死亡当年的人口中。
     * 返回 人口最多 且 最早 的年份。
     * @param logs
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumPopulation(int[][] logs) {
        int result;
        result = method_01(logs);
        return result;
    }

    /**
     * @Description:
     * 差分 + 优先队列
     * AC: 2ms/41.31MB
     * @param logs
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] logs) {
        int maxValue = 0;
        int begin = Integer.MAX_VALUE;
        for (int[] log : logs) {
            begin = Math.min(begin, log[0]);
            maxValue = Math.max(maxValue, log[1]);
        }
        int[] diff = new int[maxValue + 1];

        for (int[] log : logs) {
            diff[log[0]]++;
            diff[log[1]]--;
        }
        Queue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o2[0] - o1[0];
            } else {
                return o1[1] - o2[1];
            }
        });
        int count = 0;
        for (int i = begin; i <= maxValue; i++) {
            count += diff[i];
            if (count > 0) {
                queue.offer(new int[]{count, i});
            }
        }
        return queue.peek()[1];
    }
}
