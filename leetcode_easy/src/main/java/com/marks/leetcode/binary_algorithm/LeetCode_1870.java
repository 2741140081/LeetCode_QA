package com.marks.leetcode.binary_algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/20 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1870 {
    /**
     * @Description: [
     * 给你一个浮点数 hour ，表示你到达办公室可用的总通勤时间。要到达办公室，你必须按给定次序乘坐 n 趟列车。
     * 另给你一个长度为 n 的整数数组 dist ，其中 dist[i] 表示第 i 趟列车的行驶距离（单位是千米）。
     * 每趟列车均只能在整点发车，所以你可能需要在两趟列车之间等待一段时间。
     *
     * 例如，第 1 趟列车需要 1.5 小时，那你必须再等待 0.5 小时，搭乘在第 2 小时发车的第 2 趟列车。
     * 返回能满足你在时限前到达办公室所要求全部列车的 最小正整数 时速（单位：千米每小时），如果无法准时到达，则返回 -1 。
     *
     * 生成的测试用例保证答案不超过 107 ，且 hour 的 小数点后最多存在两位数字 。
     * ]
     * @param dist 
     * @param hour
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSpeedOnTime(int[] dist, double hour) {
        int result;
        result = method_01(dist, hour);
        result = method_02(dist, hour);
        return result;
    }

    /**
     * @Description: [
     * 官方题解:
     * AC:48ms/56.20MB
     * ]
     * @param dist
     * @param hour
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] dist, double hour) {
        int n = dist.length;
        // 将 hour 乘 100 以转为整数
        long hr = Math.round(hour * 100);
        // 时间必须要大于路程段数减 1
        if (hr <= (n - 1) * 100) {
            return -1;
        }
        // 二分
        int l = 1;
        int r = 10000000;
        while (l < r) {
            int mid = l + (r - l) / 2;
            // 判断当前时速是否满足时限
            long t = 0;
            // 前 n-1 段中第 i 段贡献的时间： ceil(dist[i] / mid)
            for (int i = 0; i < n - 1; ++i) {
                t += (dist[i] - 1) / mid + 1;
            }
            // 最后一段贡献的时间： dist[n-1] / mid
            t *= mid;
            t += dist[n - 1];
            if (t * 100 <= hr * mid) { // 通分以转化为整数比较
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l; // 满足条件的最小时速

    }

    /**
     * @Description: [
     * 输入：dist = [1,3,2], hour = 2.7
     * 输出：3
     * 解释：速度为 3 时：
     * - 第 1 趟列车运行需要 1/3 = 0.33333 小时。
     * - 由于不是在整数时间到达，故需要等待至第 1 小时才能搭乘列车。第 2 趟列车运行需要 3/3 = 1 小时。
     * - 由于是在整数时间到达，可以立即换乘在第 2 小时发车的列车。第 3 趟列车运行需要 2/3 = 0.66667 小时。
     * - 你将会在第 2.66667 小时到达。
     *
     * 错误,
     * 原因:
     * 案例 int[] dist = {1, 1, 100000}, double hour = 2.01
     * 预期输出: 10000000
     * 实际输出: 6777777
     * 原因: 因为最后的四舍五入导致改错误, 正确题解查看method_02
     * ]
     * @param dist
     * @param hour
     * @return int
     * @author marks
     * @CreateDate: 2024/11/20 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] dist, double hour) {
        int n = dist.length;
        int maxHour = (int) Math.ceil(hour);
        if (maxHour < n) {
            return -1;
        }
        int left = 1;
        int right = 10000000;
        while (left < right) {
            int mid = (right - left) / 2 + left;
            double totalHour = getTotalHour(mid, dist);
            if (totalHour <= hour) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private double getTotalHour(int mid, int[] dist) {
        double sum = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            sum += (dist[i] / mid + (dist[i] % mid == 0 ? 0 : 1));
        }
        BigDecimal end = BigDecimal.valueOf(dist[dist.length - 1]).divide(BigDecimal.valueOf(mid), 2, RoundingMode.HALF_UP);
        sum += end.doubleValue();
        return sum;
    }
}
