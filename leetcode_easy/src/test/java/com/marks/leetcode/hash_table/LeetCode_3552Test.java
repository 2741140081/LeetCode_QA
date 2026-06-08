package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3552Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/5 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3552Test {

    @Test
    void minMoves() {
//        String[] matrix = {"HH", ".C"};
//        String[] matrix = {".A.","BA.","B.A"};
        String[] matrix = {"A..#A..##.","..#..C.C.B","A.#..#BC.."};
        LeetCode_3552 leetCode_3552 = new LeetCode_3552();
        System.out.println(leetCode_3552.minMoves(matrix));

    }
}