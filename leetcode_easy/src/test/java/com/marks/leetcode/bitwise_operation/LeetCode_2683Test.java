package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/17 17:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2683Test {

    @Test
    void doesValidArrayExist() {
        int[] derived = {1, 1, 0};
        LeetCode_2683 leetCode_2683 = new LeetCode_2683();
        boolean result = leetCode_2683.doesValidArrayExist(derived);
        System.out.println(result);
        assertTrue(result);
    }
}