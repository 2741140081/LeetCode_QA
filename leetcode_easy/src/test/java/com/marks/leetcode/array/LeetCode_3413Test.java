package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3413Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/29 11:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3413Test {

    @Test
    void maximumCoins() {
        // 输入： coins = [[8,10,1],[1,3,2],[5,6,4]], k = 4
        int[][] coins = {{8,10,1},{1,3,2},{5,6,4}};
        int k = 4;
        // [[8,12,13],[29,32,2],[13,15,2],[40,41,18],[42,48,18],[33,36,11],[37,38,6]], k = 28
        coins = new int[][]{{8,12,13},{29,32,2},{13,15,2},{40,41,18},{42,48,18},{33,36,11},{37,38,6}};
        k = 28;
        // [[31,39,20],[27,29,6],[1,3,4]], k = 3
        coins = new int[][]{{31,39,20},{27,29,6},{1,3,4}};
        k = 3;

        LeetCode_3413 leetCode_3413 = new LeetCode_3413();
        long result = leetCode_3413.maximumCoins(coins, k);
        System.out.println(result);
    }
}