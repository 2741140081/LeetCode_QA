package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3453 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/13 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3453 {

    /**
     * @Description:
     * 给你一个二维整数数组 squares ，
     * 其中 squares[i] = [xi, yi, li] 表示一个与 x 轴平行的正方形的左下角坐标和正方形的边长。
     * 找到一个最小的 y 坐标，它对应一条水平线，该线需要满足它以上正方形的总面积 等于 该线以下正方形的总面积。
     * 答案如果与实际答案的误差在 10-5 以内，将视为正确答案。
     * 注意：正方形 可能会 重叠。重叠区域应该被 多次计数 。
     *
     * 1 <= squares.length <= 5 * 10^4
     * squares[i] = [xi, yi, li]
     * squares[i].length == 3
     * 0 <= xi, yi <= 10^9
     * 1 <= li <= 10^9
     * 所有正方形的总面积不超过 10^12。
     * @param: squares
     * @return double
     * @author marks
     * @CreateDate: 2026/01/13 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double separateSquares(int[][] squares) {
        double result;
        result = method_01(squares);
        return result;
    }

    /**
     * @Description:
     * 1. 想到的第一个思路是二分法, 找到一个最接近的y坐标, 然后计算该坐标下的正方形的面积.
     * 2. 对 squares 进行排序(对 y 进行升序排序), 获取最大值和最小值, 然后使用二分法, 找到最接近的y坐标.
     * AC: 154ms/145.82MB
     * @param: squares
     * @return double
     * @author marks
     * @CreateDate: 2026/01/13 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[][] squares) {
        double sum = 0; // 正方形的总面积
        double left = 0;
        double right = 0; // 最大值
        for (int[] square : squares) {
            double y = square[1];
            double l = square[2];
            sum += l * l;
            right = Math.max(right, y + l);
        }

        while (Math.abs(right - left) > 1e-5) {
            double mid = left + (right - left) / 2;
            if (sumArea(squares, mid, sum)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        return right;
    }

    private boolean sumArea(int[][] squares, double mid, double total) {
        double area = 0;
        for (int[] square : squares) {
            double y = square[1];
            double l = square[2];
            if (y >= mid) {
                continue;
            }
            area += (Math.min(l, mid - y) * l);
        }
        return area >= total / 2;
    }

}
