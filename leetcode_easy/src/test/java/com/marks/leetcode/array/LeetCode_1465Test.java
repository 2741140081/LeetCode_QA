package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1465Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 17:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1465Test {

    @Test
    void maxArea() {
        int h = 1000000000;
        int w = 1000000000;
        int[] horizontalCuts = {2};
        int[] verticalCuts = {2};
        LeetCode_1465 leetCode1465 = new LeetCode_1465();
        int result = leetCode1465.maxArea(h, w, horizontalCuts, verticalCuts);
        System.out.println(result);
    }
}