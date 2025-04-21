package com.marks.leetcode.data_structure.common_enum_technique;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 17:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2001Test {

    @Test
    void interchangeableRectangles() {
        // rectangles =[[4,5],[7,8]]
        int[][] rectangles = {{4, 5},{7, 8}};
        long result = new LeetCode_2001().interchangeableRectangles(rectangles);
        System.out.println(result);
    }
}