package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2906Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2906Test {

    @Test
    void constructProductMatrix() {
        // 输入：grid = [[1,2],[3,4]]
        //输出：[[24,12],[8,6]]
        int[][] grid = {{1, 2}, {3, 4}};
        LeetCode_2906 leetCode2906 = new LeetCode_2906();
        int[][] result = leetCode2906.constructProductMatrix(grid);
        System.out.println(Arrays.deepToString(result));
    }
}