package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1861Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 10:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1861Test {

    @Test
    void rotateTheBox() {
        // 输入：box = [["#",".","*","."],
        //            ["#","#","*","."]]
        LeetCode_1861 leetCode_1861 = new LeetCode_1861();
        char[][] box = new char[][]{
                {'#', '.', '*', '.'},
                {'#', '#', '*', '.'}
        };
        char[][] rotateTheBox = leetCode_1861.rotateTheBox(box);
    }
}