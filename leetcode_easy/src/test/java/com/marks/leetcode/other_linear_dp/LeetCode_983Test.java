package com.marks.leetcode.other_linear_dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_983Test {

    @Test
    void mincostTickets() {
        // days = [1,4,6,7,8,20], costs = [2,7,15]
        int[] days = {1,4,6,7,8,20};
        int[] costs = {2,7,15};
        int result = new LeetCode_983().mincostTickets(days, costs);
        System.out.println(result);
    }
}