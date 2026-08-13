package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1515 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/29 10:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1515 {

    /**
     * @Description:
     * 一家快递公司希望在新城市建立新的服务中心。公司统计了该城市所有客户在二维地图上的坐标，并希望能够以此为依据为新的服务中心选址：使服务中心 到所有客户的欧几里得距离的总和最小 。
     * 给你一个数组 positions ，其中 positions[i] = [xi, yi] 表示第 i 个客户在二维地图上的位置，返回到所有客户的 欧几里得距离的最小总和 。
     * 换句话说，请你为服务中心选址，该位置的坐标 [xcentre, ycentre] 需要使下面的公式取到最小值：
     * 与真实值误差在 10-5之内的答案将被视作正确答案。
     *
     * tips:
     * 1 <= positions.length <= 50
     * positions[i].length == 2
     * 0 <= xi, yi <= 100
     * @param: positions
     * @return double
     * @author marks
     * @CreateDate: 2026/07/29 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double getMinDistSum(int[][] positions) {
        double result;
        result = method_01(positions);
        return result;
    }

    /**
     * @Description:
     * 1. 通过查询得知, 使用 Weiszfeld 算法, 计算最小欧几里得距离
     * AC: 5ms/45.91MB
     * @param: positions
     * @return double
     * @author marks
     * @CreateDate: 2026/07/29 10:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[][] positions) {
        int n = positions.length;
        if (n == 1) {
            return 0;
        }
        // 初始化: 使用质心作为起始点
        double x = 0, y = 0;
        for (int[] position : positions) {
            x += position[0];
            y += position[1];
        }
        x /= n;
        y /= n;

        double eps = 1e-8;
        double prevX, prevY;
        int maxIter = 1000; // 最大迭代次数
        int iter = 0;
        while (iter < maxIter) {
            prevX = x;
            prevY = y;
            double weightSum = 0;
            double weightX = 0, weightY = 0;
            for (int[] position : positions) {
                double dx = x - position[0];
                double dy = y - position[1];

                double dist = Math.sqrt(dx * dx + dy * dy);

                // 避免除零：若当前点与某顶点重合，添加微小扰动
                if (dist < 1e-12) {
                    dist = 1e-12;
                }

                double weight = 1.0 / dist;
                weightSum += weight;
                weightX += weight * position[0];
                weightY += weight * position[1];
            }

            if (weightSum == 0) {
                break;
            }

            x = weightX / weightSum;
            y = weightY / weightSum;

            // 检查收敛, 即当前点(x, y) 与 前一个点(prevX, prevY) 之间距离足够小, 则可以视为一个点, 返回
            if (Math.sqrt((x - prevX) * (x - prevX) + (y - prevY) * (y - prevY)) < eps) {
                break;
            }
            iter++;
        }

        double ans = 0;
        for (int[] position : positions) {
            double dx = x - position[0];
            double dy = y - position[1];
            ans += Math.sqrt(dx * dx + dy * dy);
        }

        return ans;
    }

}
