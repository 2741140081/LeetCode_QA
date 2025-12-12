package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3433Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3433Test {

    @Test
    void countMentions() {
        // events =
        //[["MESSAGE","1","ALL"],["OFFLINE","66","1"],["MESSAGE","66","HERE"],["OFFLINE","5","1"]]
        LeetCode_3433 leetCode_3433 = new LeetCode_3433();
        int numberOfUsers = 3;
        List<List<String>> events = new ArrayList<>();
        events.add(Arrays.asList("MESSAGE", "1", "ALL"));
        events.add(Arrays.asList("OFFLINE", "66", "1"));
        events.add(Arrays.asList("MESSAGE", "66", "HERE"));
        events.add(Arrays.asList("OFFLINE", "5", "1"));
        int[] result = leetCode_3433.countMentions(numberOfUsers, events);
    }
}