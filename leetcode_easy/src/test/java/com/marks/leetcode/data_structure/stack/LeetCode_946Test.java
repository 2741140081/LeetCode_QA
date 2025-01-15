package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 11:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_946Test {

    @Test
    void validateStackSequences() {
        // pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
        int[] pushed = {1,2,3,4,5}, popped = {4,5,3,2,1};
        boolean result = new LeetCode_946().validateStackSequences(pushed, popped);
        System.out.println(result);
    }
}