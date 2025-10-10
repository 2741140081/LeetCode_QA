package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/9 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3494Test {

    @Test
    void minTime() {
        int[] skill = {1, 5, 2, 4};
        int[] mana = {5, 1, 4, 2};
        LeetCode_3494 leetCode_3494 = new LeetCode_3494();
        long result = 0;
        result = leetCode_3494.minTime(skill, mana);
        System.out.println("实际结果: " + result);
        assertEquals(110, result);

        skill = new int[]{1, 1, 1};
        mana = new int[]{1, 1, 1};
        result = leetCode_3494.minTime(skill, mana);
        System.out.println("实际结果: " + result);
        assertEquals(5, result);
    }
}