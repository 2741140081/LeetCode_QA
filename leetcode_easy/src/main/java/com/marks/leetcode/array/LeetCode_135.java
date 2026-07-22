package com.marks.leetcode.array;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_135 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/16 17:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_135 {

    /**
     * @Description:
     * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
     * 你需要按照以下要求，给这些孩子分发糖果：
     * 每个孩子至少分配到 1 个糖果。
     * 相邻两个孩子中，评分更高的那个会获得更多的糖果。
     * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
     * tips:
     * n == ratings.length
     * 1 <= n <= 2 * 10^4
     * 0 <= ratings[i] <= 2 * 10^4
     * @param: ratings
     * @return int
     * @author marks
     * @CreateDate: 2026/07/16 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int candy(int[] ratings) {
        int result;
        result = method_01(ratings);
        return result;
    }

    /**
     * @Description:
     * 1. 如果存在一个凹形数组, 即 i - 1, i, i + 1, ratings[i - 1] > ratings[i] && ratings[i + 1] > ratings[i],
     * 此时基于贪心的策略, candy[i] = 1, candy[i + 1] = candy[i - 1] = candy[i] + 1;
     * 2. 可以使用队列, 初始时将谷底下标添加到队列中, 并且 int[] candy 记录分发的糖果数量, 谷底的糖果数是1.
     * 3. 如何判断一个元素处于谷底, 如果 i 有左右两侧, 则满足 ratings[i - 1] > ratings[i] && ratings[i + 1] > ratings[i],
     * 那么 i 是谷底, 如果 i = 0, 则只需要判断 ratings[i + 1] > ratings[i], 如果 i = n - 1, 则只需要判断 ratings[i - 1] > ratings[i],
     * 4. 然后对队列进行广度优先搜索, 找的是最长递增序列, 例如弹出 i, 那么判断 i + 1, 和 i - 1, 如果 ratings[i + 1] > ratings[i],
     * 则 candy[i + 1] = Math.max(candy[i + 1], candy[i] + 1); 并且将 i + 1 添加到队列中;
     * 5. 时间复杂度, 每个下标都会出队和入队一次, 时间复杂度是 O(n)
     * 6. 需要考虑相等情况, 评分相等, 表示糖果数也相等
     * AC: 11ms/47.32MB
     * @param: ratings
     * @return int
     * @author marks
     * @CreateDate: 2026/07/16 17:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] ratings) {
        int n = ratings.length;
        if (n == 1) { // 处理特殊情况1
            return 1;
        }
        int[] candy = new int[n];
        // 创建队列
        Queue<Integer> queue = new ArrayDeque<>();
        // 添加谷底元素
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                if (ratings[i] <= ratings[i + 1]) {
                    queue.offer(i);
                    candy[i] = 1;
                }
            } else if (i == n - 1) {
                if (ratings[i] <= ratings[i - 1]) {
                    queue.offer(i);
                    candy[i] = 1;
                }
            } else {
                // 1 ~ n - 2
                if (ratings[i] <= ratings[i - 1] && ratings[i] <= ratings[i + 1]) {
                    queue.offer(i);
                    candy[i] = 1;
                }
            }
        }
        // 进行 BFS
        while (!queue.isEmpty()) {
            int i = queue.poll();
            if (i > 0 && ratings[i - 1] > ratings[i]) {
                candy[i - 1] = Math.max(candy[i - 1], candy[i] + 1);
                queue.offer(i - 1);
            }
            if (i < n - 1 && ratings[i + 1] > ratings[i]) {
                candy[i + 1] = Math.max(candy[i + 1], candy[i] + 1);
                queue.offer(i + 1);
            }
        }
        // 遍历 candy, 计算糖果数
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += candy[i];
        }

        return result;
    }

}
