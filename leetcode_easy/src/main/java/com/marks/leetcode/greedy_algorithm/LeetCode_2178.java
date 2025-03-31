package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 17:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2178 {
    /**
     * @Description:
     * 给你一个整数 finalSum 。请你将它拆分成若干个 互不相同 的正偶数之和，且拆分出来的正偶数数目 最多 。
     *
     * 比方说，给你 finalSum = 12 ，那么这些拆分是 符合要求 的（互不相同的正偶数且和为 finalSum）：(2 + 10) ，(2 + 4 + 6) 和 (4 + 8) 。
     * 它们中，(2 + 4 + 6) 包含最多数目的整数。注意 finalSum 不能拆分成 (2 + 2 + 4 + 4) ，因为拆分出来的整数必须互不相同。
     * 请你返回一个整数数组，表示将整数拆分成 最多 数目的正偶数数组。如果没有办法将 finalSum 进行拆分，请你返回一个 空 数组。你可以按 任意 顺序返回这些整数。
     * tips:
     * 1 <= finalSum <= 10^10
     * @param finalSum
     * @return java.util.List<java.lang.Long>
     * @author marks
     * @CreateDate: 2025/3/31 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Long> maximumEvenSplit(long finalSum) {
        List<Long> result;
        result = method_01(finalSum);
        return result;
    }

    /**
     * @Description:
     * 12
     * [2], [2, 2], [2, 2]
     * 28, 14
     * [2], [2,2] [2,2,2], [2,2,2,2], [2,2,2,2]
     * AC: 66ms/61.75MB
     *
     * 修改为 将 Set 改为 List, 因为我们不需要 去重 和排序, i += 2 已经去重和排序
     *
     * @param finalSum
     * @return java.util.List<java.lang.Long>
     * @author marks
     * @CreateDate: 2025/3/28 17:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Long> method_01(long finalSum) {
        if (finalSum % 2 != 0) {
            return new ArrayList<>();
        }
        List<Long> ans = new ArrayList<>();
        long sum = 0;
        long i = 2;
        for (; sum + i <= finalSum; i += 2) {
            ans.add(i);
            sum += i;
        }
        long sub = finalSum - sum;
        ans.set(ans.size() - 1,  ans.get(ans.size() - 1) + sub);
        return ans;
    }
}
