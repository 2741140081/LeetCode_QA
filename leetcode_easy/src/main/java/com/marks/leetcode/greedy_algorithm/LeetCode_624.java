package com.marks.leetcode.greedy_algorithm;

import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/2 16:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_624 {

    /**
     * @Description:
     * 给定 m 个数组，每个数组都已经按照升序排好序了。
     *
     * 现在你需要从两个不同的数组中选择两个整数（每个数组选一个）并且计算它们的距离。两个整数 a 和 b 之间的距离定义为它们差的绝对值 |a-b| 。
     *
     * 返回最大距离。
     * @param arrays
     * @return int
     * @author marks
     * @CreateDate: 2025/4/2 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDistance(List<List<Integer>> arrays) {
        int result;
        result = method_01(arrays);
        return result;
    }

    /**
     * @Description:
     * 不需要 O(n^2)
     *
     * AC: 18ms/67.53MB
     * @param arrays
     * @return int
     * @author marks
     * @CreateDate: 2025/4/2 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> arrays) {
        int n = arrays.size();
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        int[] pre = new int[2]; // 记录最小值 及其 index
        pre[0] = arrays.get(0).get(0);
        for (int i = 0; i < n; i++) {
            if (pre[0] > arrays.get(i).get(0)) {
                pre[0] = arrays.get(i).get(0);
                pre[1] = i;
            }
            int size = arrays.get(i).size();
            queue.add(new int[] {arrays.get(i).get(size - 1), i});
        }
        if (queue.peek()[1] != pre[1]) {
            return Math.abs(queue.peek()[0] - pre[0]);
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int minValue = arrays.get(i).get(0);
            if (queue.peek()[1] != i) {
                ans = Math.max(ans, Math.abs(queue.peek()[0] - minValue));
            } else {
                int[] temp = queue.poll();
                ans = Math.max(ans, Math.abs(queue.peek()[0] - minValue));
                queue.add(temp);
            }
        }
        return ans;
    }
}
