package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_331Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_331Test {

    @Test
    void isValidSerialization() {
        // "9,3,4,#,#,1,#,#,2,#,6,#,#"
        String preorder = "9,3,4,#,#,1,#,#,2,#,6,#,#";
        LeetCode_331 leetCode_331 = new LeetCode_331();
        boolean validSerialization = leetCode_331.isValidSerialization(preorder);
        System.out.println(validSerialization);
    }
}