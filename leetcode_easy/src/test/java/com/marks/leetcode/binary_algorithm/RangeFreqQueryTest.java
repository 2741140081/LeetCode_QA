package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/18 11:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class RangeFreqQueryTest {

    @Test
    void query() {
        // [[[12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56]], [1, 2, 4], [0, 11, 33]]
        int[] arr = {12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56};
        RangeFreqQuery query = new RangeFreqQuery(arr);
        int r1 = query.query(1, 2, 4);
        int r2 = query.query(0, 11, 33);
        System.out.println("r1:" + r1 + ", r2:" + r2);
    }
}