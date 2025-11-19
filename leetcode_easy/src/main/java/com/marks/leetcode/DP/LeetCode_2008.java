package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2008 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2008 {

    /**
     * @Description:
     * 你驾驶出租车行驶在一条有 n 个地点的路上。这 n 个地点从近到远编号为 1 到 n ，你想要从 1 开到 n ，通过接乘客订单盈利。
     * 你只能沿着编号递增的方向前进，不能改变方向。
     * 乘客信息用一个下标从 0 开始的二维数组 rides 表示，
     * 其中 rides[i] = [starti, endi, tipi] 表示第 i 位乘客需要从地点 starti 前往 endi ，愿意支付 tipi 元的小费。
     *
     * 每一位 你选择接单的乘客 i ，你可以 盈利 endi - starti + tipi 元。你同时 最多 只能接一个订单。
     * 给你 n 和 rides ，请你返回在最优接单方案下，你能盈利 最多 多少元。
     * 注意：你可以在一个地点放下一位乘客，并在同一个地点接上另一位乘客。
     *
     * tips:
     * 1 <= n <= 10^5
     * 1 <= rides.length <= 3 * 10^4
     * rides[i].length == 3
     * 1 <= starti < endi <= n
     * 1 <= tipi <= 10^5
     * @param: n
     * @param: rides
     * @return long
     * @author marks
     * @CreateDate: 2025/11/19 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxTaxiEarnings(int n, int[][] rides) {
        long result;
        result = method_01(n, rides);
        return result;
    }

    /**
     * @Description:
     * dp[i] = Math.max(dp[i - 1], dp[j] + end[j] - start[j] + tip[j])
     * 思路是对的, 但是 "超出内存限制" 64/84, 使用 HashMap 存储中间结果, 内存占用变低
     * 但是还是未通过 "超出时间限制", 继续优化
     *
     * 查看官解修改
     * AC: 90ms/112.36MB
     * @param: n
     * @param: rides
     * @return long
     * @author marks
     * @CreateDate: 2025/11/19 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[][] rides) {
        Arrays.sort(rides, Comparator.comparingInt(a -> a[1])); // 按照终点升序排序
        int m = rides.length;
        long[] dp = new long[m + 1];
        for (int i = 0; i < m; i++) {
            int j = binarySearch(rides, i, rides[i][0]);
            dp[i + 1] = Math.max(dp[i], dp[j] + rides[i][1] - rides[i][0] + rides[i][2]);
        }
        return dp[m];
    }

    private int binarySearch(int[][] rides, int high, int target) {
        int low = 0;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (rides[mid][1] >= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
