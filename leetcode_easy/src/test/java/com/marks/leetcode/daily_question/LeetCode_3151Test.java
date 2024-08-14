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
 * @data 2024/8/13 16:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3151Test {

    @Test
    void isArraySpecial() {
        int[] nums = new int[] {2, 1, 4};
        boolean result = new LeetCode_3151().isArraySpecial(nums);
        System.out.println(result);
    }
}