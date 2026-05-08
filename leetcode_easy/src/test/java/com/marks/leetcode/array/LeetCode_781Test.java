package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_781Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/8 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_781Test {

    @Test
    void numRabbits() {
        // 0,0,1,1,1
        int[] answers = {0,1,0,2,0,1,0,2,1,1};
        LeetCode_781 leetCode_781 = new LeetCode_781();
        int i = leetCode_781.numRabbits(answers);
        System.out.println(i);
    }
}