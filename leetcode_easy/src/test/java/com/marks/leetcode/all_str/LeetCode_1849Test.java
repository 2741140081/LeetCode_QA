package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1849Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 16:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1849Test {

    @Test
    void splitString() {
        // 输入：s = "050043"
        String s = "200100";
        LeetCode_1849 leetCode_1849 = new LeetCode_1849();
        boolean result = leetCode_1849.splitString(s);
        System.out.println(result);
    }
}