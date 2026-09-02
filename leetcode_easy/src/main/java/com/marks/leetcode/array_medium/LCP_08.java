package com.marks.leetcode.array_medium;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_08 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1 14:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_08 {

    /**
     * @Description:
     * 在战略游戏中，玩家往往需要发展自己的势力来触发各种新的剧情。一
     * 个势力的主要属性有三种，分别是文明等级（C），资源储备（R）以及人口数量（H）。
     * 在游戏开始时（第 0 天），三种属性的值均为 0。
     * 随着游戏进程的进行，每一天玩家的三种属性都会对应增加，我们用一个二维数组 increase 来表示每天的增加情况。
     * 这个二维数组的每个元素是一个长度为 3 的一维数组，例如 [[1,2,1],[3,4,2]] 表示第一天三种属性分别增加 1,2,1 而第二天分别增加 3,4,2。
     * 所有剧情的触发条件也用一个二维数组 requirements 表示。这个二维数组的每个元素是一个长度为 3 的一维数组，
     * 对于某个剧情的触发条件 c[i], r[i], h[i]，如果当前 C >= c[i] 且 R >= r[i] 且 H >= h[i] ，则剧情会被触发。
     * 根据所给信息，请计算每个剧情的触发时间，并以一个数组返回。如果某个剧情不会被触发，则该剧情对应的触发时间为 -1 。
     *
     * tips:
     * 1 <= increase.length <= 10000
     * 1 <= requirements.length <= 100000
     * 0 <= increase[i] <= 10
     * 0 <= requirements[i] <= 100000
     * @param: increase
     * @param: requirements
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/01 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int[] result;
        result = method_01(increase, requirements);
        return result;
    }

    /**
     * @Description:
     * 1. 前缀和 + 二分查找
     * 2. 通过 increase 数组的前缀和，得到 前缀和数组 prefixSum，prefixSum[i][j] 表示前 i 天属性 j 的前缀值.
     * 3. 由于 prefixSum 是一个递增的数组, 对于 requirements 数组中的每个元素 requirements[i]，
     * 可以在 prefixSum 中使用二分查找来找到第一个大于等于 requirements[i] 的元素的下标。
     * 4. 在3种属性中选择所需时间最大的, 即为触发时间。如果 prefixSum[n][x] < requirements[i][x]，则该剧情不会被触发，返回 -1。
     * AC: 28ms/141.84MB
     * @param: increase
     * @param: requirements
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/01 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] increase, int[][] requirements) {
        int n = increase.length, m = requirements.length;
        int[][] prefixSum = new int[n + 1][3];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                prefixSum[i][j] = prefixSum[i - 1][j] + increase[i - 1][j];
            }
        }
        int[] result = new int[m];

        for (int i = 0; i < m; i++) {
            int left = 0, right = n;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (prefixSum[mid][0] >= requirements[i][0] &&
                        prefixSum[mid][1] >= requirements[i][1] &&
                        prefixSum[mid][2] >= requirements[i][2]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            boolean flag = true;
            for (int j = 0; j < 3; j++) {
                if (prefixSum[left][j] < requirements[i][j]) {
                    flag = false;
                    break;
                }
            }
            result[i] = flag ? left : -1;
        }

        return result;
    }

}
