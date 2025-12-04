package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3625 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 14:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3625 {

    /**
     * @Description:
     * 给你一个二维整数数组 points，其中 points[i] = [xi, yi] 表示第 i 个点在笛卡尔平面上的坐标。
     * 返回可以从 points 中任意选择四个不同点组成的梯形的数量。
     * 梯形 是一种凸四边形，具有 至少一对 平行边。两条直线平行当且仅当它们的斜率相同。
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2025/12/03 14:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countTrapezoids(int[][] points) {
        int result;
        result = method_01(points);
        return result;
    }

    /**
     * @Description:
     * 1. 计算肯定是将斜率相同的一组点进行分组, 然后统计每一个组中的梯形数量
     * 2. slope: 斜率 = (y2 - y1) / (x2 - x1), 但是这个得出的是一个double类型的数据,
     * @param: points
     * @return int
     * @author marks
     * @CreateDate: 2025/12/03 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] points) {

        return 0;
    }

}
