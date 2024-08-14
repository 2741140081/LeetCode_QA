package com.marks.leetcode.classic_linear_DP.LCS;

import com.marks.leetcode.array.LeetCode_11;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 11:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_10Test {

    @Test
    void isMatch() {
        String s = "aab";
        String p = "c*a*b";
        boolean result = new LeetCode_10().isMatch(s, p);
        System.out.println(result);
    }
}