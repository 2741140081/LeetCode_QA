package com.marks.leetcode.partition_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/20 10:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_132Test {

    @Test
    void minCut() {
        String s = "aaabaa";
        int result = new LeetCode_132().minCut(s);
        System.out.println(result);
    }
}