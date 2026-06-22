package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_6Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/17 15:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_6Test {

    @Test
    void convert() {
        // 输入：s = "PAYPALISHIRING", numRows = 3
        String s = "PAYPALISHIRING";
        int numRows = 3;
        LeetCode_6 leetCode_6 = new LeetCode_6();
        String result = leetCode_6.convert(s, numRows);
        System.out.println(result);
    }
}