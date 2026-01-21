package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2943Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2943Test {

    @Test
    void maximizeSquareHoleArea() {
        // 输入: n = 2, m = 1, hBars = [2,3], vBars = [2]
        int n = 4;
        int m = 40;
        int[] hBars = new int[]{5, 2, 3, 4};
        int[] vBars = new int[]{36, 41, 6, 34, 33};
        LeetCode_2943 leetCode_2943 = new LeetCode_2943();
        int result = leetCode_2943.maximizeSquareHoleArea(n, m, hBars, vBars);
        System.out.println(result);
    }
}