package com.marks.leetcode.data_structure.heap_advance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/7 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_630Test {

    @Test
    void scheduleCourse() {
        int[][] courses = {{1,2}, {2,3}};
        int result = new LeetCode_630().scheduleCourse(courses);
        System.out.println(result);
    }
}