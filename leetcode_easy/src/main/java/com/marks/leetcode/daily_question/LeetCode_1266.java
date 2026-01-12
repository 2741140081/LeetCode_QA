package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1266 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 14:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1266 {

    /**
     * @Description:
     * 平面上有 n 个点，点的位置用整数坐标表示 points[i] = [xi, yi] 。
     * 请你计算访问所有这些点需要的 最小时间（以秒为单位）。
     * 你需要按照下面的规则在平面上移动：
     * 每一秒内，你可以：
     * 沿水平方向移动一个单位长度，或者
     * 沿竖直方向移动一个单位长度，或者
     * 跨过对角线移动 sqrt(2) 个单位长度（可以看作在一秒内向水平和竖直方向各移动一个单位长度）。
     * 必须按照数组中出现的顺序来访问这些点。
     * 在访问某个点时，可以经过该点后面出现的点，但经过的那些点不算作有效访问。
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2026/01/12 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTimeToVisitAllPoints(int[][] points) {
        int result;
        result = method_01(points);
        return result;
    }

    /**
     * @Description:
     * 1. 必须按照数组中出现的顺序来访问这些点, 所以不能使用贪心算法
     * 2. 走对角线更省时间, 所以尽量使用对角线, 初始点是 points[0] = {x0, y0}, 需要移动到下一个点point[1] = {x1, y1}
     * 计算两个点之间的距离, int sx = Math.abs(x1 - x0), int sy = Math.abs(y1 - y0);
     * int t1 = Math.min(sx, sy) + Math.abs(sx - sy); int t2 = Math.max(sx, sy) + Math.abs(sx - sy);
     * {1,1} 移动到 {3,4}, t1 = 3 + 1 = 4, t2 = 4 + 1 = 5. 所以应该用 t1
     * AC: 1ms/44.19MB
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2026/01/12 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] points) {
        int ans = 0;
        int n = points.length;
        int currX = points[0][0];
        int currY = points[0][1];
        for (int i = 1; i < n; i++) {
            int x = points[i][0];
            int y = points[i][1];
            int sx = Math.abs(x - currX);
            int sy = Math.abs(y - currY);
            // ans += Math.min(sx, sy) + Math.abs(sx - sy);
            ans += Math.max(sx, sy); // 优化, Math.min(sx, sy) + Math.abs(sx - sy); 等价于 Math.max(sx, sy)
            // 更新当前位置
            currX = x;
            currY = y;
        }
        return ans;
    }

}
