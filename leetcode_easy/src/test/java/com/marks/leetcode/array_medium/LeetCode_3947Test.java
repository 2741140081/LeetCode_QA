package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3947Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/20 14:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3947Test {

    @Test
    void maximumSaleItems() {
        // 输入： items = [[1,6],[2,4],[3,5]], budget = 19
        // 输入： items = [[2,6],[4,12],[4,9],[4,15],[4,9],[4,9],[4,13]], budget = 51
        int[][] items = {{2,6},{4,12},{4,9},{4,15},{4,9},{4,9},{4,13}};
        int budget = 51;
        // 输出： 4
        // 解释： 在预算内，你可以购买 2 个物品：[2,4] 和 [3,5]。你可以免费获得物品 [1,6]，物品的总数为 2 + 2 = 4。
        LeetCode_3947 solution = new LeetCode_3947();
        int result = solution.maximumSaleItems(items, budget);
        System.out.println(result);
    }
}