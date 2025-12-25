package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_901 </p>
 * <p>文件名称:  </p>
 * <p>描述:
 * 设计一个算法收集某些股票的每日报价，并返回该股票当日价格的 跨度 。
 * 当日股票价格的 跨度 被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 * 例如，如果未来 7 天股票的价格是 [100,80,60,70,60,75,85]，那么股票跨度将是 [1,1,1,2,1,4,6] 。
 * 实现 StockSpanner 类：
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/29 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class StockSpanner {
    private Deque<int[]> stack;
    private int idx;

    /**
     * @Description:
     * StockSpanner() 初始化类对象。
     * 构建一个单调递减栈, 栈中保存价格及其下标(天数)
     * AC: 25ms/53.43MB
     * @return
     * @author marks
     * @CreateDate: 2025/12/25 10:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public StockSpanner() {
        stack = new ArrayDeque<>();
        stack.push(new int[]{-1, Integer.MAX_VALUE});
        idx = -1;
    }

    public int next(int price) {
        idx++;
        while (!stack.isEmpty() && price >= stack.peek()[1]) {
            stack.poll();
        }
        int ans = idx - stack.peek()[0]; // 应为有 -1 在栈底, 所以栈不为空
        stack.push(new int[]{idx, price});
        return ans;
    }



}
