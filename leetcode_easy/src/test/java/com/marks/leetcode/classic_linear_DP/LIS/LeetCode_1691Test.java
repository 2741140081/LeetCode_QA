package com.marks.leetcode.classic_linear_DP.LIS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 10:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1691Test {

    @Test
    void maxHeight() {
        // cuboids = [[50,45,20],[95,37,53],[45,23,12]]
        int[][] cuboids = {{50,45,20}, {95,37,53}, {45,23,12}};
        int result = new LeetCode_1691().maxHeight(cuboids);
        System.out.println(result);
    }
}