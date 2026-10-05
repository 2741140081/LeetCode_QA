package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1936 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/22 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1936 {

    /**
     * @Description:
     * 给你一个 严格递增 的整数数组 rungs ，用于表示梯子上每一台阶的 高度 。
     * 当前你正站在高度为 0 的地板上，并打算爬到最后一个台阶。
     * 另给你一个整数 dist 。每次移动中，你可以到达下一个距离你当前位置（地板或台阶）不超过 dist 高度的台阶。
     * 当然，你也可以在任何正 整数 高度处插入尚不存在的新台阶。
     * 返回爬到最后一阶时必须添加到梯子上的 最少 台阶数。
     *
     * tips:
     * 1 <= rungs.length <= 10^5
     * 1 <= rungs[i] <= 10^9
     * 1 <= dist <= 10^9
     * rungs 严格递增
     * @param: rungs
     * @param: dist
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int addRungs(int[] rungs, int dist) {
        int result;
        result = method_01(rungs, dist);
        return result;
    }

    /**
     * @Description:
     * 1. 如果相邻两个台阶的高度差大于 dist，则需要在它们之间添加台阶。
     * 2. 计算需要添加的台阶数，并累加到结果中。
     * 3. 假设相邻之间的高度差为 gap, 则需要添加的台阶数为 (gap - 1) / dist
     * AC: 1ms/68.84MB
     * @param: rungs
     * @param: dist
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] rungs, int dist) {
        int ans = 0;
        int prev = 0; // 初始位于地板
        for (int rung : rungs) {
            int gap = rung - prev; // 当前台阶与前一个台阶的高度差
            if (gap > dist) {
                // 计算需要添加的台阶数
                int addStairs = (gap - 1) / dist;
                ans += addStairs;
            }
            prev = rung; // 更新前一个台阶的高度
        }

        return ans;
    }

}
