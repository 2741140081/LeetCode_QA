package com.marks.leetcode.data_structure.common_enum_technique;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/8 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1014 {
    /**
     * @Description: 给你一个正整数数组 values，其中 values[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的 距离 为 j - i。
     *
     * 一对景点（i < j）组成的观光组合的得分为 values[i] + values[j] + i - j ，也就是景点的评分之和 减去 它们两者之间的距离。
     *
     * 返回一对观光景点能取得的最高分。
     * @param values
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScoreSightseeingPair(int[] values) {
        int result;
        result = method_01(values);
        return result;
    }

    /**
     * @Description:
     * E1: values = [8,1,5,2,6]
     * 8, 1, 7, 5, 10
     * 8 + 1 + 0 - 1, 8 + 5 + 0 - 2, 8 + 2 + 0 - 3, 8 + 6 + 0 - 4 => [8, 0, 3, -1, 2]
     *                1 + 5 + 1 - 2, 1 + 2 + 1 - 3, 1 + 6 + 1 - 4 =>    [2, 3, -1, 2]
     *                               5 + 2 + 2 - 1, 5 + 6 + 2 - 4 =>       [7, -1, 2]
     *                                                                         [5, 2]
     *                                                                            [10]
     * AC:4ms/52.43MB
     * @param values
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] values) {
        int ans = 0;
        int ma = values[0] + 0;
        for (int i = 1; i < values.length; i++) {
            ans = Math.max(ans, ma + values[i] - i);
            ma = Math.max(ma, values[i] + i);
        }
        return ans;
    }
}
