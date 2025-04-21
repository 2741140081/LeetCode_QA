package com.marks.leetcode.data_structure.heap_advance;

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
 * @date 2025/2/12 9:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1705 {
    /**
     * @Description:
     * 有一棵特殊的苹果树，一连 n 天，每天都可以长出若干个苹果。在第 i 天，树上会长出 apples[i] 个苹果，
     * 这些苹果将会在 days[i] 天后（也就是说，第 i + days[i] 天时）腐烂，变得无法食用。
     * 也可能有那么几天，树上不会长出新的苹果，此时用 apples[i] == 0 且 days[i] == 0 表示。
     *
     * 你打算每天 最多 吃一个苹果来保证营养均衡。注意，你可以在这 n 天之后继续吃苹果。
     *
     * 给你两个长度为 n 的整数数组 days 和 apples ，返回你可以吃掉的苹果的最大数目。
     * @param apples
     * @param days
     * @return int
     * @author marks
     * @CreateDate: 2025/2/12 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int eatenApples(int[] apples, int[] days) {
        int result;
        result = method_01(apples, days);
        result = method_02(apples, days);
        return result;
    }

    /**
     * @Description:
     * 官方题解:
     *
     * @param apples
     * @param days
     * @return int
     * @author marks
     * @CreateDate: 2025/2/12 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] apples, int[] days) {
        return 0;
    }

    /**
     * @Description:
     * 思路, 优先队列保存 第 i 天剩下的苹果, 小根堆 => 腐烂时间排序
     * 1. 优先吃掉即将腐烂的苹果
     * 2. 在吃之前, 现将 end_i <= i, 给移除队列, 将腐烂的苹果移除
     * 3. 如果吃完i天苹果还剩余, 将剩余的苹果添加到队列中
     * 4. 如果 apples[i] > 0, 则添加到队列中
     * 5. 队列中可能存在剩余的苹果, 需要吃完
     *
     * AC: 120ms/44.97MB
     * 看了下平均的时间复杂度在34ms, 看看官解怎么处理的
     * 好吧, 刚刚改了一下每天吃一个苹果的代码, 不需要每次都取出来, 然后放进去
     * 只需要判断是否 queue.peek()[1] == 1, 如果 true, 则poll, 否则 queue.peek()[1]--
     * 这样可以避免重复出入队
     *
     * AC: 47ms/45.23MB
     * @param apples
     * @param days
     * @return int
     * @author marks
     * @CreateDate: 2025/2/12 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] apples, int[] days) {
        int ans = 0;
        int n = apples.length;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));

        for (int i = 0; i < n; i++) {
            // 添加当天获取的苹果, 及其腐烂时间
            if (apples[i] > 0) {
                queue.offer(new int[] {days[i] + i, apples[i]});
            }

            // 移除已经腐烂的苹果
            while (!queue.isEmpty() && i >= queue.peek()[0]) {
                queue.poll();
            }

            // 每天吃一个苹果
            if (!queue.isEmpty()) {
                if (queue.peek()[1] == 1) {
                    queue.poll();
                } else {
                    queue.peek()[1]--;
                }
                ans++;
            }
        }
        // 将队列中剩余的苹果吃完
        int day = n;
        while (!queue.isEmpty()) {

            // 移除已经腐烂的苹果
            while (!queue.isEmpty() && day >= queue.peek()[0]) {
                queue.poll();
            }

            // 每天吃一个苹果
            if (!queue.isEmpty()) {
                if (queue.peek()[1] == 1) {
                    queue.poll();
                } else {
                    queue.peek()[1]--;
                }
                ans++;
            }

            day++;
        }
        return ans;
    }
}
