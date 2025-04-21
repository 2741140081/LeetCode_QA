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
 * @date 2025/1/20 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1249Test {

    @Test
    void minRemoveToMakeValid() {
        String s = "(a(b(c)d)";
        String result = new LeetCode_1249().minRemoveToMakeValid(s);
        System.out.println(result);
    }
}