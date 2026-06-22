package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_468Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_468Test {

    @Test
    void validIPAddress() {
        LeetCode_468 leetCode_468 = new LeetCode_468();
        String IP = "2001:0db8:85a3:0:0:8A2E:0370:7334:";
        String result = leetCode_468.validIPAddress(IP);
        System.out.println(result);
    }
}