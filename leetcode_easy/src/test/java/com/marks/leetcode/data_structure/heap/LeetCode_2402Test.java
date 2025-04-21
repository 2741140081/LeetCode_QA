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
 * @date 2025/2/11 15:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2402Test {

    @Test
    void mostBooked() {
        int n = 5;
        int[][] meetings = {{40,47},{7,16},{27,38},{16,43},{38,40},{2,25}};
        int result = new LeetCode_2402().mostBooked(n, meetings);
        System.out.println(result);
    }
}