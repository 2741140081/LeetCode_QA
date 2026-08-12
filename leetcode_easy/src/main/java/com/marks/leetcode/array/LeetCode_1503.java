package com.marks.leetcode.array;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1503 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/11 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1503 {

    /**
     * @Description:
     * 有一块木板，长度为 n 个 单位 。
     * 一些蚂蚁在木板上移动，每只蚂蚁都以 每秒一个单位 的速度移动。
     * 其中，一部分蚂蚁向 左 移动，其他蚂蚁向 右 移动。
     * 当两只向 不同 方向移动的蚂蚁在某个点相遇时，它们会同时改变移动方向并继续移动。
     * 假设更改方向不会花费任何额外时间。
     * 而当蚂蚁在某一时刻 t 到达木板的一端时，它立即从木板上掉下来。
     * 给你一个整数 n 和两个整数数组 left 以及 right 。
     * 两个数组分别标识向左或者向右移动的蚂蚁在 t = 0 时的位置。
     * 请你返回最后一只蚂蚁从木板上掉下来的时刻。
     * tips:
     * 1 <= n <= 10^4
     * 0 <= left.length <= n + 1
     * 0 <= left[i] <= n
     * 0 <= right.length <= n + 1
     * 0 <= right[i] <= n
     * 1 <= left.length + right.length <= n + 1
     * left 和 right 中的所有值都是唯一的，并且每个值 只能出现在二者之一 中。
     * @param: n
     * @param: left
     * @param: right
     * @return int
     * @author marks
     * @CreateDate: 2026/08/11 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getLastMoment(int n, int[] left, int[] right) {
        int result;
        result = method_01(n, left, right);
        return result;
    }

    /**
     * @Description:
     * 1. 这种通常使用优先队列
     * 2. 由于是立即折返 A, B 都是向右, C 向左, 最终会 A 向左, B,C 向右,
     * A 会在 AC 的中点处改变方向, AB 距离 x1, BC 距离 x2, 如果距离是奇数, 会多移动 1 个距离时间,
     * 但是碰撞后整数时的位置还是 int end = start - d/2; 例如 1 和 4, 碰撞会移动1.5, 然后在移动 0.5 到达整数位置处, 此时2, 3 是最终位置,
     * 移动的距离是 2.
     * 3. x1 和 x2, 第一次碰撞 BC, 此时 x2 % 2 == 0 ? x2/2 : x2/2 + 1, 并且奇数时AB 的距离会缩小 x1 - 1, 偶数时不变.
     * 应该考虑是整体
     * AC: 0ms/46.26MB
     * @param: n
     * @param: left
     * @param: right
     * @return int
     * @author marks
     * @CreateDate: 2026/08/11 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] left, int[] right) {
        int ans = 0;
        for (int p : left) {
            ans = Math.max(ans, p);
        }
        for (int p : right) {
            ans = Math.max(ans, n - p);
        }
        return ans;
    }

}
