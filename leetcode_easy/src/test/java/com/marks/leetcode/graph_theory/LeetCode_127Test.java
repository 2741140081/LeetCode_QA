package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_127Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 10:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_127Test {

    @Test
    void ladderLength() {
        // beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
        String beginWord = "hit";
        String endWord = "cog";
        String[] wordList = {"hot","dot","dog","lot","log","cog"};
        LeetCode_127 leetCode_127 = new LeetCode_127();
        int result = leetCode_127.ladderLength(beginWord, endWord, java.util.Arrays.asList(wordList));
        System.out.println(result);
    }
}