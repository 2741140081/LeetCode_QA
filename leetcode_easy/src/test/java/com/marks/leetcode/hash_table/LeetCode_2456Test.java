package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2456Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2456Test {

    @Test
    void mostPopularCreator() {
        // 输入：creators = ["alice","bob","alice","chris"], ids = ["one","two","three","four"], views = [5,10,5,4]
        LeetCode_2456 leetCode_2456 = new LeetCode_2456();
        List<List<String>> strings = leetCode_2456.mostPopularCreator(new String[]{"alice", "bob", "alice", "chris"}, new String[]{"one", "two", "three", "four"}, new int[]{5, 10, 5, 4});
    }
}