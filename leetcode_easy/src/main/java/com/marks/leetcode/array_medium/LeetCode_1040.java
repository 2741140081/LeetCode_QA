package com.marks.leetcode.array_medium;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1040 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/21 14:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1040 {

    /**
     * @Description:
     * 在 X 轴上有一些不同位置的石子。给定一个整数数组 stones 表示石子的位置。
     * 如果一个石子在最小或最大的位置，称其为 端点石子。每个回合，
     * 你可以将一颗 端点石子 拿起并移动到一个未占用的位置，使得该石子不再是一颗 端点石子。
     * 值得注意的是，如果石子像 stones = [1,2,5] 这样，你将 无法 移动位于位置 5 的端点石子，
     * 因为无论将它移动到任何位置（例如 0 或 3），该石子都仍然会是端点石子。
     * 当你无法进行任何移动时，即，这些石子的位置连续时，游戏结束。
     * 以长度为 2 的数组形式返回答案，其中：
     * answer[0] 是你可以移动的最小次数
     * answer[1] 是你可以移动的最大次数。
     *
     * tips:
     * 3 <= stones.length <= 10^4
     * 1 <= stones[i] <= 10^9
     * stones 的值各不相同。
     * @param: stones
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/21 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] numMovesStonesII(int[] stones) {
        int[] result;
        result = method_01(stones);
        return result;
    }

    /**
     * @Description:
     * 1. 最大移动次数, 根据贪心, 需要寻找 4 个点, 分别是 a, b, c, d 其中 a, d是端点石子, b, c 分别是离a, d 最近的点(b, c 可以是同一个点),
     * 此时计算分别计算 [a, c] 和 [b, d] 的剩余空闲位置数, 然后取较大值, 这个值即为最大移动次数
     * 2. 最小移动次数, 要使得移动次数最小, 那么必定是在一个区间/窗口中, 存在最多数量的石子, 其它石子不断移动到这个区间/窗口中, 此时的移动次数最小
     * 3. 先对 stones 进行排序, 方便计算[a, c] 以及 [b,d] 的剩余空闲位置数
     * 4. 使用队列的 FIFO 性质, 处理 stones[i], 判断队列顶部元素与 stones[i] 的差值与 n 的关系, 如果距离超过 n, 则需要弹出队列顶部元素
     * 5. 更新 ans[0] = n - queue.size(), ans[0] 的初始值设置为 n(移动n次必定可以移动完)
     * 6. 最小移动次数存在问题, eg: [3,4,5,6,10] 因为 10 不能直接移动到 2 或者 7 的位置, 所以单次移动不能成功
     * AC: 16ms/47.3MB
     * @param: stones
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/21 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] stones) {
        int[] ans = new int[2];
        int n = stones.length;
        // 排序
        Arrays.sort(stones);
        // 得到[a,c] 以及[b,d] 的区间剩余数目
        int maxMoves = Math.max(stones[n - 2] - stones[0] - n + 2, stones[n - 1] - stones[1] - n + 2);
        ans[1] = maxMoves;
        // 计算最小移动次数, 窗口的大小是 n, 需要一个队列(FIFO)来存储
        Queue<Integer> queue = new ArrayDeque<>();
        ans[0] = n;
        for (int stone : stones) {
            while (!queue.isEmpty() && stone - queue.peek() >= n) {
                queue.poll();
            }
            queue.add(stone);
            // 需要特殊处理 size == n - 1 的情况
            if (queue.size() == n - 1 && !queue.isEmpty()) {
                // 判断队列的首项和结尾项直接的差值
                int diff = stone - queue.peek() + 1;
                if (diff == n - 1) {
                    ans[0] = Math.min(ans[0], 2); // 需要执行2次
                    continue;
                }
            }
            ans[0] = Math.min(ans[0], n - queue.size());
        }

        return ans;
    }

}
