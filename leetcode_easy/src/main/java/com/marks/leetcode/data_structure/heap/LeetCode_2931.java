package com.marks.leetcode.data_structure.heap;

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
 * @date 2025/2/11 11:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2931 {
    /**
     * @Description:
     * 给你一个下标从 0 开始大小为 m * n 的整数矩阵 values ，表示 m 个不同商店里 m * n 件不同的物品。每个商店有 n 件物品，第 i 个商店的第 j 件物品的价值为 values[i][j] 。
     * 除此以外，第 i 个商店的物品已经按照价值非递增排好序了，也就是说对于所有 0 <= j < n - 1 都有 values[i][j] >= values[i][j + 1] 。
     * 每一天，你可以在一个商店里购买一件物品。具体来说，在第 d 天，你可以：
     *
     * 选择商店 i 。
     * 购买数组中最右边的物品 j ，开销为 values[i][j] * d 。换句话说，选择该商店中还没购买过的物品中最大的下标 j ，并且花费 values[i][j] * d 去购买。
     * 注意，所有物品都视为不同的物品。比方说如果你已经从商店 1 购买了物品 0 ，你还可以在别的商店里购买其他商店的物品 0 。
     *
     * 请你返回购买所有 m * n 件物品需要的 最大开销 。
     * @param values
     * @return long
     * @author marks
     * @CreateDate: 2025/2/11 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxSpending(int[][] values) {
        long result;
        result = method_01(values);
        return result;
    }

    /**
     * @Description:
     * 求最大值, 即最贵的最后卖, 优先卖最便宜的
     *
     * AC: 43ms/60.28MB
     * @param values
     * @return long
     * @author marks
     * @CreateDate: 2025/2/11 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] values) {
        int m = values.length;
        int n = values[0].length;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0])); // 升序排序
        for (int i = 0; i < m; i++) {
            queue.offer(new int[]{values[i][n - 1], i, n - 1});
        }
        long d = 1;
        long ans = 0;
        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            ans += (curr_node[0] * d++);
            int x = curr_node[1];
            int next_y = curr_node[2] - 1;
            if (next_y >= 0) {
                queue.offer(new int[] {values[x][next_y], x, next_y});
            }
        }
        return ans;
    }
}
