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
 * @data 2024/10/9 11:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2140Test {
    @Test
    void mostPoints() {
        // questions = [[3, 2], [4, 3], [4, 4], [2, 5]] ：
        int[][] questions = {{3, 2}, {4, 3}, {4, 4}, {2, 5}};
        long result = new LeetCode_2140().mostPoints(questions);
        System.out.println(result);
    }

}