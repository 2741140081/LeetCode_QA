package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1653Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/27 10:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1653Test {

    @Test
    void minimumDeletions() {
        // 输入：s = "aababbab"
        String s = "aababbab";
        LeetCode_1653 leetCode_1653 = new LeetCode_1653();
        int result = leetCode_1653.minimumDeletions(s);
        System.out.println(result);
    }
}