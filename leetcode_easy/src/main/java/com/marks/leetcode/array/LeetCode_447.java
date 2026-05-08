package com.marks.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_447 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_447 {

    /**
     * @Description:
     * 给定平面上 n 对 互不相同 的点 points ，其中 points[i] = [xi, yi] 。
     * 回旋镖 是由点 (i, j, k) 表示的元组 ，其中 i 和 j 之间的欧式距离和 i 和 k 之间的欧式距离相等（需要考虑元组的顺序）。
     * 返回平面上所有回旋镖的数量。
     *
     * tips:
     * n == points.length
     * 1 <= n <= 500
     * points[i].length == 2
     * -10^4 <= xi, yi <= 10^4
     * 所有点都 互不相同
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfBoomerangs(int[][] points) {
        int result;
        result = method_01(points);
        return result;
    }

    /**
     * @Description:
     * 1. 计算两个点之间的距离, 用欧式距离公式计算, int dist = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
     * AC: 144ms/46.34MB
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] points) {
        int ans = 0;
        for (int[] p1 : points) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int[] p2 : points) {
                int dist = (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
                map.put(dist, map.getOrDefault(dist, 0) + 1);
            }
            for (Integer value : map.values()) {
                ans += value * (value - 1);
            }
        }
        return ans;
    }

}
