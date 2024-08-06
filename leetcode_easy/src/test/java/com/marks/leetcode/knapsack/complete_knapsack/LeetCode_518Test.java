package com.marks.leetcode.knapsack.complete_knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/6 17:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_518Test {

    @Test
    void change() {
        int[] coins = new int[] {1, 2, 5};
        int amount = 5;
        int change = new LeetCode_518().change(amount, coins);
        System.out.println(change);
    }
}