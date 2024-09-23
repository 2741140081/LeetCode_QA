package com.marks.leetcode.partition_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 16:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1416Test {

    @Test
    void numberOfArrays() {
        // s = "1317", k = 2000
        // s = "1000", k = 10000
        String s = "1000";
        int k = 10000;
        int result = new LeetCode_1416().numberOfArrays(s, k);
        System.out.println(result);
    }
}