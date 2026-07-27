package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_849 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/24 16:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_849 {

    /**
     * @Description:
     * 给你一个数组 seats 表示一排座位，
     * 其中 seats[i] = 1 代表有人坐在第 i 个座位上，seats[i] = 0 代表座位 i 上是空的（下标从 0 开始）。
     * 至少有一个空座位，且至少有一人已经坐在座位上。
     * 亚历克斯希望坐在一个能够使他与离他最近的人之间的距离达到最大化的座位上。
     * 返回他到离他最近的人的最大距离。
     *
     * tips:
     * 2 <= seats.length <= 2 * 10^4
     * seats[i] 为 0 或 1
     * 至少有一个 空座位
     * 至少有一个 座位上有人
     * @param: seats
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int maxDistToClosest(int[] seats) {
        int result;
        result = method_01(seats);
        return result;
    }

    /**
     * @Description:
     * 1. 坐在左右两个端点, 如果两个端点为0, 此时计算最大距离
     * 2. 统计被1包围的最长连续 0 的个数
     * AC: 2ms/47.05MB
     * @param: seats
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_01(int[] seats) {
        int n = seats.length;
        // left, right 分别记录第一个和最后一个 seats[i] == 1 的位置
        int left = 0, right = n - 1;
        while (seats[left] == 0) left++;
        while (seats[right] == 0) right--;
        int ans = Math.max(left, n - 1 - right);
        int cnt = 0; // 统计 [left ~ right] 范围内, 最长连续0的个数
        int max = 0;
        for (int i = left; i <= right; i++) {
            if (seats[i] == 0) {
                cnt++;
                max = Math.max(max, cnt);
            } else {
                cnt = 0;
            }
        }
        ans = Math.max(ans, (max + 1) / 2);

        return ans;
    }

}
