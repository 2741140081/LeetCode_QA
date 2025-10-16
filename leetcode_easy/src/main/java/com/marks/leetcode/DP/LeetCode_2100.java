package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/14 16:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2100 {

    /**
     * @Description:
     * 你和朋友们准备去野炊。给你一个下标从 0 开始的整数数组 security ，其中 security[i] 是第 i 天的建议出行指数。
     * 日子从 0 开始编号。同时给你一个整数 time 。
     *
     * 如果第 i 天满足以下所有条件，我们称它为一个适合野炊的日子：
     *
     * 第 i 天前和后都分别至少有 time 天。
     * 第 i 天前连续 time 天建议出行指数都是非递增的。
     * 第 i 天后连续 time 天建议出行指数都是非递减的。
     * 更正式的，第 i 天是一个适合野炊的日子当且仅当：security[i - time] >= security[i - time + 1] >= ... >= security[i] <= ... <= security[i + time - 1] <= security[i + time].
     *
     * 请你返回一个数组，包含 所有 适合野炊的日子（下标从 0 开始）。返回的日子可以 任意 顺序排列。
     * @param security
     * @param time
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/10/14 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        List<Integer> result;
        result = method_01(security, time);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：security = [5,3,3,3,5,6,2], time = 2
     * 输出：[2,3]
     * 1. 假设我构建一个前缀数组, 记录当前的递增天数, 和 递减天数
     * AC: 8ms/56.72MB
     * @param security
     * @param time
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/10/14 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] security, int time) {
        int n = security.length;
        int[] increase = new int[n];
        int[] decrease = new int[n];
        for (int i = 1; i < n; i++) {
            if (security[i] <= security[i - 1]) {
                decrease[i] = decrease[i - 1] + 1;
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (security[i] <= security[i + 1]) {
                increase[i] = increase[i + 1] + 1;
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = time; i < n - time; i++) {
            if (increase[i] >= time && decrease[i] >= time) {
                ans.add(i);
            }
        }

        return ans;
    }


}
