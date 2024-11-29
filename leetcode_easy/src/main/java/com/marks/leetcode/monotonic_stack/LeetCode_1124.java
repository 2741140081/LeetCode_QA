package com.marks.leetcode.monotonic_stack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/29 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1124 {
    /**
     * @Description: [
     * 给你一份工作时间表 hours，上面记录着某一位员工每天的工作小时数。
     * 我们认为当员工一天中的工作小时数大于 8 小时的时候，那么这一天就是「劳累的一天」。
     * 所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
     *
     * 请你返回「表现良好时间段」的最大长度。
     * tips:
     * 1 <= hours.length <= 10^4
     * 0 <= hours[i] <= 16
     * ]
     * @param hours 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestWPI(int[] hours) {
        int result;
        result = method_01(hours);
        return result;
    }

    /**
     * @Description: [
     * 输入：hours = [9,9,6,0,6,6,9]
     * 输出：3
     * 解释：最长的表现良好时间段是 [9,9,6]。
     * hours = {1, 1, 0, 0, 0, 0, 1}
     * pre[] = {1, 2, 2, 2, 2, 2, 3}
     *
     *
     * ]
     * @param hours
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] hours) {
        int n = hours.length;
        int[] pre = new int[n];
        pre[0] = hours[0] > 8 ? 1 : 0;
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + hours[i] > 8 ? 1 : 0;
        }

        return 0;
    }
}
