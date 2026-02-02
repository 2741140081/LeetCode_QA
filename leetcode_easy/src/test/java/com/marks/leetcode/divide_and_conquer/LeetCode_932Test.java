package com.marks.leetcode.divide_and_conquer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_932Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_932Test {

    @Test
    void beautifulArray() {
        int n = 5;
        int[] result = new LeetCode_932().beautifulArray(n);
        System.out.println(Arrays.toString(result));
    }
}