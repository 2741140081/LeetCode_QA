package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3660Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/3 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3660Test {

    @Test
    void maxValue() {
        // nums = {30,21,5,35,24}
        int[] nums = {30,21,5,35,24};
        int[] result = new LeetCode_3660().maxValue(nums);
        // 将结果数组输出
        System.out.println(Arrays.toString(result));
    }
}