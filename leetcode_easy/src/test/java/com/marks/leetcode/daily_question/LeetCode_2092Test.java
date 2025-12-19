package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2092Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 16:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2092Test {

    @Test
    void findAllPeople() {
        // 输入：n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
        int n = 5;
        int[][] meetings = {{3,4,2},{1,2,1},{2,3,1}};
        int firstPerson = 1;
        LeetCode_2092 leetCode_2092 = new LeetCode_2092();
        List<Integer> result = leetCode_2092.findAllPeople(n, meetings, firstPerson);
    }
}