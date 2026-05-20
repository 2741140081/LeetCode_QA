package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_040Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/15 11:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCR_040Test {

    @Test
    void maximalRectangle() {
        // 输入：matrix = ["10100","10111","11111","10010"]
        String[] matrix = {"10100", "10111", "11111", "10010"};
        LCR_040 lcr_040 = new LCR_040();
        int result = lcr_040.maximalRectangle(matrix);
        System.out.println(result);
    }
}