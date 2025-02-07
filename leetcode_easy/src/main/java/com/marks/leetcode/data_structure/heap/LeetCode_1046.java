package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 16:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1046 {
    /**
     * @Description:
     * 有一堆石头，每块石头的重量都是正整数。
     *
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     *
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     * @param stones 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lastStoneWeight(int[] stones) {
        int result;
        result = method_01(stones);
        return result;
    }

    /**
     * @Description:
     * AC:2ms/40.31MB
     * @param stones
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] stones) {
        int n = stones.length;
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < n; i++) {
            queue.offer(stones[i]);
        }
        while (queue.size() >= 2) {
            int first = queue.poll();
            int second = queue.poll();
            if (first != second) {
                queue.offer(first - second);
            }
        }
        return queue.size() == 1 ? queue.peek() : 0;
    }
}
