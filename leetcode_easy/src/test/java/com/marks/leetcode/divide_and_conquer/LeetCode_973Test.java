package com.marks.leetcode.divide_and_conquer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_973Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 10:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_973Test {

    @Test
    void kClosest() {
        // 输入：points = [[3,3],[5,-1],[-2,4]], k = 2
        // 输出：[[3,3],[-2,4]]
        int[][] points = {{3, 3}, {5, -1}, {-2, 4}};
        int[][] ans = new LeetCode_973().kClosest(points, 2);
        System.out.println(Arrays.deepToString(ans));
    }
}