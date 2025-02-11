package com.marks.leetcode.data_structure.heap_advance;

import java.util.Arrays;
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
 * @date 2025/2/11 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_502 {

    /**
     * @Description:
     *
     * 假设 力扣（LeetCode）即将开始 IPO 。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。
     * 给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
     * 最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
     * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
     * 答案保证在 32 位有符号整数范围内。
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return int
     * @author marks
     * @CreateDate: 2025/2/11 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int result;
        result = method_01(k, w, profits, capital);
        result = method_02(k, w, profits, capital);
        return result;
    }

    /**
     * @Description:
     * AC: 116ms/57.59MB
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return int
     * @author marks
     * @CreateDate: 2025/2/11 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        int[][] pre = new int[n][n];

        for (int i = 0; i < n; i++) {
            pre[i][0] = capital[i];
            pre[i][1] = profits[i];
        }
        Arrays.sort(pre, Comparator.comparingInt(o -> o[0]));
        int index = 0;
        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        while (k > 0) {
            while (index < n && w >= pre[index][0]) {
                queue.offer(pre[index++][1]);
            }
            if (!queue.isEmpty()) {
                w += queue.poll();
            } else {
                break;
            }
            k--;
        }
        return w;
    }

    /**
     * @Description:
     * 优先队列, 大根堆
     * AC: 158ms/62.11MB
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return int
     * @author marks
     * @CreateDate: 2025/2/11 16:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        Queue<int[]> pre = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o2[1] - o1[1];
            }
        });
        for (int i = 0; i < n; i++) {
            pre.offer(new int[]{capital[i], profits[i]});
        }

        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        while (k > 0) {
            while (!pre.isEmpty() && w >= pre.peek()[0]) {
                queue.offer(pre.poll()[1]);
            }
            if (!queue.isEmpty()) {
                w += queue.poll();
            }
            k--;
        }
        return w;
    }
}
