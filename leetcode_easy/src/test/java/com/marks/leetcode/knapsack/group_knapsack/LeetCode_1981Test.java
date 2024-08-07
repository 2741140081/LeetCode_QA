package com.marks.leetcode.knapsack.group_knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1981Test {

    @Test
    void minimizeTheDifference() {
        // 输入：mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13
        int[][] mat = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int target = 13;
        int result = new LeetCode_1981().minimizeTheDifference(mat, target);
        System.out.println(result);
    }
}