package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_452 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/30 11:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_452 {

    /**
     * @Description:
     * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，
     * 其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y 坐标。
     *
     * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆 。
     * 可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
     *
     * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2026/04/30 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMinArrowShots(int[][] points) {
        int result;
        result = method_01(points);
        return result;
    }

    /**
     * @Description:
     * 1. 按照 x_end 进行排序, 因为 points[i][1] >= points[i][0]
     * AC: 60ms/93.25MB
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2026/04/30 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] points) {
        // 对 points[][] 排序, 按照 points[i][1] 升序排序
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        int ans = 0;
        for (int i = 0; i < points.length;) {
            int shot = points[i][1]; // 射击点的x坐标
            while (i < points.length && points[i][0] <= shot) {
                i++;
            }
            ans++;
        }

        return ans;
    }

}
