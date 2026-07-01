package com.marks.leetcode.array;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3771 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/1 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3771 {

    /**
     * @Description:
     * 给你一个 正整数 hp 和两个 正整数 数组 damage 和 requirement，数组下标从 1 开始。
     * 有一个地牢，里面有 n 个陷阱房间，编号从 1 到 n。进入编号为 i 的房间会使你的生命值减少 damage[i]。
     * 减少后，如果你的剩余生命值至少为 requirement[i]，你可以从该房间获得 1 分。
     * 定义 score(j) 为从房间 j 开始，依次进入房间 j, j + 1, ..., n 时可以获得的总分。
     * 返回整数 score(1) + score(2) + ... + score(n)，即从所有起始房间计算的分数总和。
     * 注意： 你不能跳过房间。即使你的生命值降为非正数，你仍然可以继续进入房间。
     * tips:
     * 1 <= hp <= 10^9
     * 1 <= n == damage.length == requirement.length <= 10^5
     * 1 <= damage[i], requirement[i] <= 10^4
     * @param: hp
     * @param: damage
     * @param: requirement
     * @return long
     * @author marks
     * @CreateDate: 2026/07/01 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long totalScore(int hp, int[] damage, int[] requirement) {
        long result;
        result = method_01(hp, damage, requirement);
        return result;
    }

    /**
     * @Description:
     * 1. hp 是定值, 并且每进入一个房间后, hp 会减少
     * 2. long sum 为 damage[] 前缀和, 需要添加优先队列, 队列是小根堆, 存入的是 hp 的值.
     * 3. 当前处理 i, 前 i - 1 个 damage[] 之和是 sum, 将 hp + sum 添加到队列中
     * 4. sum += damage[i], while sum + req[i] > pq.peek(), pq.poll(), 将所有小于 sum + req[i] 的 值弹出
     * 5. 剩余的值都是合法值, 相当于分数, long ans 存储总分数, ans += pq.size();
     * 6. 存在问题, 即 sum + requirement[i] 可能会比 sum + requirement[j] 更大, (i < j), 这就导致提前出队了, 导致 ans 值错误
     * 7. 想不到如何处理, 看看提示
     * 8. 构建前缀和数组, 存储 damage[] 的前缀和, 对于 假设有一个区间[i, j], 对于 j, 在 j 处, 剩余的 remainderHp >= requirement[j],
     * hp - (preSum[j] - preSum[i]) >= requirement[j] => preSum[i] >= requirement[j] - hp + preSum[j]
     * 9. 由于 long target = requirement[j] - hp + preSum[j] 是一个定值, 需要在 preSum 中通过二分查找, 找到最小大于等于 target 的下标 i,
     * [i, j] 集合都可以在 j 处获得分数, ans += j - i + 1; 整体时间复杂度是 O(nlogn)
     * AC: 64ms/176.26MB
     * @param: hp
     * @param: damage
     * @param: requirement
     * @return long
     * @author marks
     * @CreateDate: 2026/07/01 9:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int hp, int[] damage, int[] requirement) {
        int n = damage.length;
        long[] preSum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + damage[i - 1];
        }
        long ans = 0;
        for (int j = 1; j <= n; j++) {
            long target = requirement[j - 1] - hp + preSum[j];

            int left = 0, right = j - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (preSum[mid] >= target) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            if (left < j && preSum[left] >= target) {
                ans += j - left;
            }
        }

        return ans;
    }

}
