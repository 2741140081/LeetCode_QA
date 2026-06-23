package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_809Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 15:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_809Test {

    @Test
    void expressiveWords() {
        // s =
        //"dddiiiinnssssssoooo"
        //words =
        //["dinnssoo","ddinso","ddiinnso","ddiinnssoo","ddiinso","dinsoo","ddiinsso","dinssoo","dinso"]
        String s = "dddiiiinnssssssoooo";
        String[] words = {"dinnssoo","ddinso","ddiinnso","ddiinnssoo","ddiinso","dinsoo","ddiinsso","dinssoo","dinso"};
        LeetCode_809 leetCode_809 = new LeetCode_809();
        int count = leetCode_809.expressiveWords(s, words);
        System.out.println(count);
    }
}