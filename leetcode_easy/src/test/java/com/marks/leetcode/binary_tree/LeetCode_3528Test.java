package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/30 16:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3528Test {

    @Test
    void baseUnitConversions() {
        int[][] conversions = {{0,1,2},{0,2,3},{1,3,4},{1,4,5},{2,5,2},{4,6,3},{5,7,4}};
        int[] result = new LeetCode_3528().baseUnitConversions(conversions);
    }
}