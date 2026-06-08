package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3664Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 10:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3664Test {

    @Test
    void score() {
        // 输入： cards = ["aa","ab","ba","ac"], x = "a"
        String[] cards = {"aa", "ab", "ba", "ac"};
        char x = 'a';
        LeetCode_3664 leetCode_3664 = new LeetCode_3664();
        int score = leetCode_3664.score(cards, x);
        System.out.println(score);
        // 输入： cards = ["aa","ab","ba"], x = "a"
        String[] cards1 = {"ab","aa","ab","bc","cc","bc","bb","ac","bc","bc","aa","aa","ba","bc","cb","ba","ac","bb","cb","ac","cb","cb","ba","bc","ca","ba","bb","cc","cc","ca","ab","bb","bc","ba","ac","bc","ac","ac","bc","bb","bc","ac","bc","aa","ba","cc","ac","bb","ba","bb"};
        char x1 = 'b';
//        int score1 = leetCode_3664.score(cards1, x1);
//        System.out.println(score1);
    }
}