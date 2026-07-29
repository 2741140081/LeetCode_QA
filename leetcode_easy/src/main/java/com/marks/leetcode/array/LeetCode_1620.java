package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1620 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/29 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1620 {

    /**
     * @Description:
     * 给你一个数组 towers 和一个整数 radius 。
     * 数组  towers  中包含一些网络信号塔，其中 towers[i] = [xi, yi, qi] 表示第 i 个网络信号塔的坐标是 (xi, yi) 且信号强度参数为 qi 。
     * 所有坐标都是在  X-Y 坐标系内的 整数 坐标。两个坐标之间的距离用 欧几里得距离 计算。
     * 整数 radius 表示一个塔 能到达 的 最远距离 。
     * 如果一个坐标跟塔的距离在 radius 以内，那么该塔的信号可以到达该坐标。
     * 在这个范围以外信号会很微弱，所以 radius 以外的距离该塔是 不能到达的 。
     * 如果第 i 个塔能到达 (x, y) ，那么该塔在此处的信号为 ⌊qi / (1 + d)⌋ ，其中 d 是塔跟此坐标的距离。
     * 一个坐标的 信号强度 是所有 能到达 该坐标的塔的信号强度之和。
     * 请你返回数组 [cx, cy] ，表示 信号强度 最大的 整数 坐标点 (cx, cy) 。
     * 如果有多个坐标网络信号一样大，请你返回字典序最小的 非负 坐标。
     * 注意：
     * 坐标 (x1, y1) 字典序比另一个坐标 (x2, y2) 小，需满足以下条件之一：
     * 要么 x1 < x2 ，
     * 要么 x1 == x2 且 y1 < y2 。
     * ⌊val⌋ 表示小于等于 val 的最大整数（向下取整函数）
     *
     * tips:
     * 1 <= towers.length <= 50
     * towers[i].length == 3
     * 0 <= xi, yi, qi <= 50
     * 1 <= radius <= 50
     * @param: towers
     * @param: radius
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/29 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] bestCoordinate(int[][] towers, int radius) {
        int[] result;
        result = method_01(towers, radius);
        return result;
    }

    /**
     * @Description:
     * 1. 由于数据范围很小, 所以采用枚举的方式, 枚举 坐标(x, y) 的所有可能性, 然后计算每个点的信号强度
     * 2. x, y 的取值范围是 0 ~ 50, 信号最强的点在几何图形的内部, 而不是外部, 计算信号强度是(x, y) 和坐标点 towers[i] = [xi, yi, qi], 先计算欧几里得距离
     * d = Math.sqrt(dx * dx + dy * dy), dx = xi - x, dy = yi - y.
     * 3. 时间复杂度是, 一共有 2n * 2n 个坐标点, 每个坐标点需要对 n 进行for循环, 所以整体复杂度是 O(n^3) 不会超时
     * 4. 不清楚 ⌊qi / (1 + d)⌋, 其中 ⌊⌋ 是向下取整
     * AC: 33ms/42.96MB
     * @param: towers
     * @param: radius
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/29 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] towers, int radius) {
        int m = 51;
        int pMax = 0;
        int[] ans = new int[2];
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < m; y++) {
                int currP = 0;
                for (int[] tower : towers) {
                    int dx = tower[0] - x;
                    int dy = tower[1] - y;
                    int d = dx * dx + dy * dy;
                    if (d <= radius * radius) {
                        double dSqrt = Math.sqrt(d) + 1;
                        currP += (int) Math.floor(tower[2] / dSqrt);
                    }
                }
                if (pMax < currP) {
                    ans[0] = x;
                    ans[1] = y;
                    pMax = currP;
                }
            }
        }

        return ans;
    }

}
