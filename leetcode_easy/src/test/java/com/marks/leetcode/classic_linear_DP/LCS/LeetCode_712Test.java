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
 * @data 2024/8/9 14:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_712Test {

    @Test
    void minimumDeleteSum() {
        // s1 = "delete", s2 = "leet"
        String s1 = "delete";
        String s2 = "leet";
        int result = new LeetCode_712().minimumDeleteSum(s1, s2);
        System.out.println(result);
    }
}