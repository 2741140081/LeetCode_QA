package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_756Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/29 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_756Test {

    @Test
    void pyramidTransition() {
        // bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
        LeetCode_756 leetCode_756 = new LeetCode_756();
        boolean result = leetCode_756.pyramidTransition("AAAA", List.of("AAB", "AAC", "BCD", "BBE", "DEF"));

        System.out.println(result);
    }
}