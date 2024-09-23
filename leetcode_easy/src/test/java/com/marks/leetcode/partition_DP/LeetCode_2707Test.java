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
 * @data 2024/9/20 16:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2707Test {

    @Test
    void minExtraChar() {
        // s = "leetscode", dictionary = ["leet","code","leetcode"]
        String s = "leetscode";
        String[] dictionary = {"leet", "code", "leetcode"};
        int result = new LeetCode_2707().minExtraChar(s, dictionary);
        System.out.println(result);
    }
}