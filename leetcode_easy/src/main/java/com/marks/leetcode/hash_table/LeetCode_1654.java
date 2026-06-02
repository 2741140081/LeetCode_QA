package com.marks.leetcode.hash_table;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1654 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 16:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1654 {

    /**
     * @Description:
     * 有一只跳蚤的家在数轴上的位置 x 处。请你帮助它从位置 0 出发，到达它的家。
     * 跳蚤跳跃的规则如下：
     * 它可以 往前 跳恰好 a 个位置（即往右跳）。
     * 它可以 往后 跳恰好 b 个位置（即往左跳）。
     * 它不能 连续 往后跳 2 次。
     * 它不能跳到任何 forbidden 数组中的位置。
     * 跳蚤可以往前跳 超过 它的家的位置，但是它 不能跳到负整数 的位置。
     *
     * 给你一个整数数组 forbidden ，其中 forbidden[i] 是跳蚤不能跳到的位置，同时给你整数 a， b 和 x ，
     * 请你返回跳蚤到家的最少跳跃次数。如果没有恰好到达 x 的可行方案，请你返回 -1
     *
     * tips:
     * 1 <= forbidden.length <= 1000
     * 1 <= a, b, forbidden[i] <= 2000
     * 0 <= x <= 2000
     * forbidden 中所有位置互不相同。
     * 位置 x 不在 forbidden 中。
     * @param: forbidden
     * @param: a
     * @param: b
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 16:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        int result;
        result = method_01(forbidden, a, b, x);
        return result;
    }

    /**
     * @Description:
     * 1. 由于不能连续向后跳跃2次, 所以需要讨论 a, b 的大小关系 a < b, 总体是向前跳跃的
     * 2. 使用 Set 集合存储无法到达的位置
     * 3. 如果我们到达某个位置, 当第二次再次可能到达当前位置时, 此时该位置无法到达, 即将所有已到的位置加入 Set 集合中
     * 4. 然后进行广度优先搜索, 搜索到 x 时返回 true
     * 5. 怎么判断无法到达的情况? 需要讨论 a b 的大小关系来得到结论:
     * 6.1 a >= b 时, 总体是向前的, 当位置超过 x + b 是, 超过的坐标是无效坐标
     * 6.2 a < b 时, x * a - y * b = X 或者 说 (x * a - X) % b == 0;
     * 例如, a = 3, b = 15, x = 9, (3x - 9) % 15 == 0; 求 x 的可能值 x = 0, x = 8, x = 13 ...., 即存在无数个 x 的可能值, 也就是说
     * 当前 b > a 时, 必定存在解, 需要找到最小的解
     * 7. a = 29, b = 98, x = 80 (29x - 80) % 98 == 0; 98y = 29x - 80; => y = (29x - 80) / 98;
     * 8. 查看官方题解, 需要计算当 a < b时, 存在哟个上限值, 没仔细看具体如何到达.
     * 9. 然后就是 visited 集合, 需要记录方向结果即, 可以使用dict = 1 or -1 来得到结果.
     * AC: 29ms/46.83MB
     * @param: forbidden
     * @param: a
     * @param: b
     * @param: x
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 16:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] forbidden, int a, int b, int x) {
        Set<Integer> set = new HashSet<>();
        int max = forbidden[0];
        for (int i : forbidden) {
            set.add(i);
            max = Math.max(max, i);
        }
        int upper = Math.max(max + a, x) + b;
        // 创建优先队列, int[] 表示 [步数, 当前位置, 连续后跳步数]
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        queue.offer(new int[] {0, 0, 1}); // 起始位置 0 处, 当前跳跃步数为 0, 方向 1 表示向前, -1 表示向后
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int step = point[0];
            int curr = point[1];
            int dict = point[2];
            if (curr == x) {
                return step;
            }

            int nextPosition = curr + a;
            int nextDict = 1;
            // 向前跳跃, 判断下一个位置是否有效, 以及是否越界
            if (nextPosition <= upper && !visited.contains(nextDict * nextPosition) && !set.contains(nextPosition)) {
                queue.offer(new int[] {step + 1, nextPosition, nextDict}); // dict 修改为 0
                // 添加到 visited 集合中
                visited.add(nextPosition);
            }
            // 向后跳跃, 判断是否越界, 是否在 set 集合, 以及当前 dict 是否等于 1

            if (dict == 1) {
                nextDict = -1;
                nextPosition = curr - b;
                if (nextPosition > 0 && nextPosition <= upper && !visited.contains(nextDict * nextPosition) && !set.contains(curr - b)) {
                    queue.offer(new int[] {step + 1, curr - b, nextDict}); // dict 修改为 -1
                    // 添加到 visited 集合中
                    visited.add(nextPosition * nextDict); // 需要添加 dict 方向问题才行, 否则会与正向的重复导致结果错误
                }
            }
        }

        return -1;
    }

}
