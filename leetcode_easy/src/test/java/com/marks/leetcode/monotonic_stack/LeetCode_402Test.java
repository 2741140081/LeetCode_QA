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
 * @date 2024/12/6 15:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_402Test {

    @Test
    void removeKdigits() {
        String num = "1432219";
        int k = 3;
        String result = new LeetCode_402().removeKdigits(num, k);
        System.out.println(result);
    }
}