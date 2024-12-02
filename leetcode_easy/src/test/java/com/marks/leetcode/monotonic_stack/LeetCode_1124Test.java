package com.marks.leetcode.monotonic_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/29 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1124Test {

    @Test
    void longestWPI() {
        // hours = [9,9,6,0,6,6,9]
        int[] hours = {6, 6, 6};
        int result = new LeetCode_1124().longestWPI(hours);
        System.out.println(result);
    }
}