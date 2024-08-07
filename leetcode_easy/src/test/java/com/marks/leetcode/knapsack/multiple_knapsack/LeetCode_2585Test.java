package com.marks.leetcode.knapsack.multiple_knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 14:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2585Test {

    @Test
    void waysToReachTarget() {
        // target = 6, types = [[6,1],[3,2],[2,3]]
        int target = 6;
        int[][] types= new int[][]{{6, 1}, {3, 2}, {2, 3}};
        int result = new LeetCode_2585().waysToReachTarget(target, types);
        System.out.println(result);
    }
}