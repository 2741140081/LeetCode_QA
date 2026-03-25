package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1871Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1871Test {

    @Test
    void canReach() {
        // 输入：s = "01101110", minJump = 2, maxJump = 3
        LeetCode_1871 leetCode_1871 = new LeetCode_1871();
        boolean result = leetCode_1871.canReach("0000000000", 8, 8);
        System.out.println(result);
    }
}