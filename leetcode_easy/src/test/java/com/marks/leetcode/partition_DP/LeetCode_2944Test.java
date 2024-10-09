package com.marks.leetcode.partition_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 17:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2944Test {

    @Test
    void minimumCoins() {
        int[] prices = {26,18,6,12,49,7,45,45};
        int result = new LeetCode_2944().minimumCoins(prices);
        System.out.println(result);
    }
}