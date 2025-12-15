package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2110 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2110 {

    /**
     * @Description:
     * 给你一个整数数组 prices ，表示一支股票的历史每日股价，其中 prices[i] 是这支股票第 i 天的价格。
     * 一个 平滑下降的阶段 定义为：对于 连续一天或者多天 ，每日股价都比 前一日股价恰好少 1 ，这个阶段第一天的股价没有限制。
     * 请你返回 平滑下降阶段 的数目。
     * @param: prices
     * @return long
     * @author marks
     * @CreateDate: 2025/12/15 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public long getDescentPeriods(int[] prices) {
        long result;
        result = method_01(prices);
        return result;
    }

    /**
     * @Description:
     * E1:输入：prices = [3,2,1,4]
     * 输出：7
     * 解释：总共有 7 个平滑下降阶段：
     * [3], [2], [1], [4], [3,2], [2,1] 和 [3,2,1]
     * 注意，仅一天按照定义也是平滑下降阶段。
     * 1. 假设有 x 天是平滑下降阶段, 那么阶段数是多少?
     * x + x - 1 + x - 2 + ... + 1 = (1 + x) * x / 2
     * AC: 3ms/82.77MB
     * @param: prices
     * @return long
     * @author marks
     * @CreateDate: 2025/12/15 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] prices) {
        long ans = 0;
        int n = prices.length;
        long count = 1;
        for (int i = 1; i < n; i++) {
            if (prices[i] == prices[i - 1] - 1) {
                count++;
            } else {
                ans += ((1 + count) * count / 2);
                count = 1;
            }
        }
        // 结束阶段
        ans += ((1 + count) * count / 2);
        return ans;
    }

}
