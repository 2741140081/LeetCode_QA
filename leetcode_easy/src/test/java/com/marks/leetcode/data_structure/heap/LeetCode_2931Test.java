package com.marks.leetcode.data_structure.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/11 11:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2931Test {

    @Test
    void maxSpending() {
        // values = {{8,5,2},{6,4,1},{9,7,3}}
        int[][] values = {{8,5,2},{6,4,1},{9,7,3}};
        long result = new LeetCode_2931().maxSpending(values);
        System.out.println(result);
    }
}