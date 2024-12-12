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
 * @date 2024/11/26 10:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1802Test {

    @Test
    void maxValue() {
        int n = 6, index = 2,  maxSum = 931384943;
//        int n = 8, index = 7,  maxSum = 14;
        int result = new LeetCode_1802().maxValue(n, index, maxSum);
        System.out.println(result);
    }
}