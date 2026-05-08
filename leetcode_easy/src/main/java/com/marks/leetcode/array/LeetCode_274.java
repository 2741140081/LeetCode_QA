package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_274 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_274 {

    /**
     * @Description:
     * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。
     * 根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，并且 至少 有 h 篇论文被引用次数大于等于 h 。
     * 如果 h 有多种可能的值，h 指数 是其中最大的那个。
     *
     * tips:
     * n == citations.length
     * 1 <= n <= 5000
     * 0 <= citations[i] <= 1000
     * @param: citations
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int hIndex(int[] citations) {
        int result;
        result = method_01(citations);
        return result;
    }

    /**
     * @Description:
     * 1. 排序 + 计数
     * AC: 5ms/42.79MB
     * @param: citations
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] citations) {
        int n = citations.length;
        // 排序
        Arrays.sort(citations);
        int ans = 0;
        int count = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (citations[i] >= count) {
                ans = Math.max(ans, count);
                count++;
            } else {
                break;
            }
        }

        return ans;
    }

}
