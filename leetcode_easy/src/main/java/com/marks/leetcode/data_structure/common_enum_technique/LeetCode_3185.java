package com.marks.leetcode.data_structure.common_enum_technique;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/8 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3185 {
    /**
     * @Description: 
     * 给你一个整数数组 hours，表示以 小时 为单位的时间，返回一个整数，表示满足 i < j 且 hours[i] + hours[j] 构成 整天 的下标对 i, j 的数目。
     *
     * 整天 定义为时间持续时间是 24 小时的 整数倍 。
     *
     * 例如，1 天是 24 小时，2 天是 48 小时，3 天是 72 小时，以此类推。
     *
     * tips:
     * 1 <= hours.length <= 5 * 10^5
     * 1 <= hours[i] <= 10^9
     * 1 <= hours.length <= 5 * 10^5
     * 1 <= hours[i] <= 10^9
     * @param hours
     * @return long
     * @author marks
     * @CreateDate: 2025/1/8 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countCompleteDayPairs(int[] hours) {
        long result;
        result = method_01(hours);
        return result;
    }

    /**
     * @Description:
     *
     * AC:4ms/94.88MB
     * @param hours 
     * @return long
     * @author marks
     * @CreateDate: 2025/1/8 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] hours) {
        int[] pre = new int[24];
        long ans = 0;
        for (int hour : hours) {
            int key = hour % 24;
            int temp_key = 24 - key;
            if (key == 0 && pre[key] > 0) {
                ans += pre[key];
            } else if (key != 0 && pre[temp_key] > 0) {
                ans += pre[temp_key];
            }
            pre[key]++;
        }
        return ans;
    }
}
