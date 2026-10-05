package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_836 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/14 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_836 {

    /**
     * @Description:
     * 矩形以列表 [x1, y1, x2, y2] 的形式表示，其中 (x1, y1) 为左下角的坐标，(x2, y2) 是右上角的坐标。
     * 矩形的上下边平行于 x 轴，左右边平行于 y 轴。
     * 如果相交的面积为 正 ，则称两矩形重叠。需要明确的是，只在角或边接触的两个矩形不构成重叠。
     * 给出两个矩形 rec1 和 rec2 。如果它们重叠，返回 true；否则，返回 false 。
     *
     * tips:
     * rect1.length == 4
     * rect2.length == 4
     * -109 <= rec1[i], rec2[i] <= 10^9
     * rec1 和 rec2 表示一个面积不为零的有效矩形
     * @param: rec1
     * @param: rec2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/14 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        boolean result;
        result = method_01(rec1, rec2);
        return result;
    }

    /**
     * @Description:
     * 1. 先分别得到两个矩形的4个坐标点，然后判断是否重叠
     * 2. 反向思考, 不重叠的条件, rec1 的右下角坐标在 rec2的左上角坐标
     * AC: 0ms/41.96MB
     * @param: rec1
     * @param: rec2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/14 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] rec1, int[] rec2) {
        return (Math.min(rec1[2], rec2[2]) > Math.max(rec1[0], rec2[0]) &&
                Math.min(rec1[3], rec2[3]) > Math.max(rec1[1], rec2[1]));
    }

}
