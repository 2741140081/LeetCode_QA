package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1648Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/30 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1648Test {

    @Test
    void maxProfit() {
        // 输入：inventory = [2,8,4,10,6], orders = 20
        int[] inventory = {497978859,167261111,483575207,591815159};
        int orders = 836556809;
        int result = new LeetCode_1648().maxProfit(inventory, orders);
        System.out.println(result);
    }
}