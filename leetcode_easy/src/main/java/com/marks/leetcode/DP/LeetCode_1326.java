package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1326 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/16 11:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1326 {

    /**
     * @Description:
     * 在 x 轴上有一个一维的花园。花园长度为 n，从点 0 开始，到点 n 结束。
     * 花园里总共有 n + 1 个水龙头，分别位于 [0, 1, ..., n] 。
     * 给你一个整数 n 和一个长度为 n + 1 的整数数组 ranges ，
     * 其中 ranges[i] （下标从 0 开始）表示：如果打开点 i 处的水龙头，可以灌溉的区域为 [i -  ranges[i], i + ranges[i]] 。
     *
     * 请你返回可以灌溉整个花园的 最少水龙头数目 。如果花园始终存在无法灌溉到的地方，请你返回 -1 。
     *
     * tips:
     * 1 <= n <= 10^4
     * ranges.length == n + 1
     * 0 <= ranges[i] <= 100
     * @param: n
     * @param: ranges
     * @return int
     * @author marks
     * @CreateDate: 2026/04/16 11:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTaps(int n, int[] ranges) {
        int result;
        result = method_01(n, ranges);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. 创建所有水龙头能覆盖的范围, 并且对范围进行排序(升序排序)
     * 3. 每次贪心的选择最右侧的端点, 并且将这个点作为下一次的起始点, 初始起始点为0.
     * AC: 21ms/46.22MB
     * @param: n
     * @param: ranges
     * @return int
     * @author marks
     * @CreateDate: 2026/04/16 11:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] ranges) {
        int[][] arrs = new int[n + 1][2];
        for (int i = 0; i < n + 1; i++) {
            int left = i - ranges[i];
            int right = i + ranges[i];
            arrs[i][0] = left;
            arrs[i][1] = right;
        }
        Arrays.sort(arrs, (o1, o2) -> o1[0] - o2[0]);
        int index = 0;
        int count = 0;
        while (index < n) {
            int maxRight = -1;
            for (int i = 0; i <= n; i++) {
                if (arrs[i][0] <= index && index <= arrs[i][1]) {
                    maxRight = Math.max(maxRight, arrs[i][1]);
                }
            }
            if (maxRight <= index) {
                return -1;
            }
            index = maxRight;
            count++;
        }
        return count;
    }

}
