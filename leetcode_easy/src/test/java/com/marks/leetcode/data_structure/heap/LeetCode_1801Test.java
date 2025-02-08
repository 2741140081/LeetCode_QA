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
 * @date 2025/2/8 15:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1801Test {

    @Test
    void getNumberOfBacklogOrders() {
        int[][] orders = {{7,1000000000,1},{15,3,0},{5,999999995,0},{5,1,1}};
        int result = new LeetCode_1801().getNumberOfBacklogOrders(orders);
        System.out.println(result);
    }
}