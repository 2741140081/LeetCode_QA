package com.marks.leetcode.array;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2462 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/3 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2462 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 costs ，其中 costs[i] 是雇佣第 i 位工人的代价。
     * 同时给你两个整数 k 和 candidates 。我们想根据以下规则恰好雇佣 k 位工人：
     * 总共进行 k 轮雇佣，且每一轮恰好雇佣一位工人。
     * 在每一轮雇佣中，从最前面 candidates 和最后面 candidates 人中选出代价最小的一位工人，如果有多位代价相同且最小的工人，选择下标更小的一位工人。
     * 比方说，costs = [3,2,7,7,1,2] 且 candidates = 2 ，第一轮雇佣中，我们选择第 4 位工人，因为他的代价最小 [3,2,7,7,1,2] 。
     * 第二轮雇佣，我们选择第 1 位工人，因为他们的代价与第 4 位工人一样都是最小代价，而且下标更小，[3,2,7,7,2] 。注意每一轮雇佣后，剩余工人的下标可能会发生变化。
     * 如果剩余员工数目不足 candidates 人，那么下一轮雇佣他们中代价最小的一人，如果有多位代价相同且最小的工人，选择下标更小的一位工人。
     * 一位工人只能被选择一次。
     * 返回雇佣恰好 k 位工人的总代价。
     *
     * tips:
     * 1 <= costs.length <= 10^5
     * 1 <= costs[i] <= 10^5
     * 1 <= k, candidates <= costs.length
     * @param: costs
     * @param: k
     * @param: candidates
     * @return long
     * @author marks
     * @CreateDate: 2026/07/03 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long totalCost(int[] costs, int k, int candidates) {
        long result;
        result = method_01(costs, k, candidates);
        return result;
    }

    /**
     * @Description:
     * 1. 这种数据范围注定不能使用 O(n^2) 的解法
     * 2. 使用模拟的方式, 复杂度是多少? 每插入一个元素, 时间复杂度是 O(n), 舍弃; 如果使用优先队列, 每次插入一个元素
     * 3. 由于需要分别从首尾添加元素, 那么当仅考虑一侧数据时, 例如只从头部取出 k 位工人, 那么顺序是什么, 如何取
     * 4. 算了直接用优先队列进行模拟, int left, right 分别指向待添加元素下标
     * AC: 80ms/82.84MB
     * @param: costs
     * @param: k
     * @param: candidates
     * @return long
     * @author marks
     * @CreateDate: 2026/07/03 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] costs, int k, int candidates) {
        int n = costs.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        int left = candidates - 1, right = n - candidates;
        // 判断 left 与 right 是否重叠
        if (left + 1 >= right) {
            // 存在重叠取余, 所有元素都添加进优先队列
            for (int i = 0; i < n; i++) {
                pq.add(new int[]{costs[i], i});
            }
        } else {
            // 分别添加左右两侧的 candidates 个元素
            for (int i = 0; i <= left; i++) {
                pq.add(new int[]{costs[i], i});
            }
            for (int i = right; i < n; i++) {
                pq.add(new int[]{costs[i], i});
            }
        }
        long ans = 0;
        for (int i = 0; i < k; i++) {
            int[] min = pq.poll(); // {cost, id}
            ans += min[0];
            if (left + 1 < right) {
                if (min[1] <= left) {
                    // 左侧添加元素
                    left++;
                    pq.add(new int[]{costs[left], left}); // 是否会与right重叠? 存在可能性, 需要使用 left + 1 与 right 对比
                } else {
                    // 右侧添加元素
                    right--;
                    pq.add(new int[]{costs[right], right});
                }
            }
        }

        return ans;
    }

}
