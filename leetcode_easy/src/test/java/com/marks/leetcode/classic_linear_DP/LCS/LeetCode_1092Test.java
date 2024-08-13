package com.marks.leetcode.classic_linear_DP.LCS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/12 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1092Test {

    @Test
    void shortestCommonSupersequence() {
        String str1 = "abac";
        String str2 = "cab";
        String result = new LeetCode_1092().shortestCommonSupersequence(str1, str2);
        System.out.println(result);
    }
}