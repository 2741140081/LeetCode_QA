package com.marks.leetcode.monotonic_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/2 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_84Test {

    @Test
    void largestRectangleArea() {
        // heights = [2,1,5,6,2,3]
        int[] heights = {2,1,5,6,2,3};
        int result = new LeetCode_84().largestRectangleArea(heights);
        System.out.println(result);
    }
}