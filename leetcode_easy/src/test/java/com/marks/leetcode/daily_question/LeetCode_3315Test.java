package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3315Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/21 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3315Test {

    @Test
    void minBitwiseArray() {
        // nums = [2,3,5,7, 11, 13, 31]
        List<Integer> nums = new ArrayList<>();
        nums = List.of(2, 3, 5, 7, 11, 13, 31);
        LeetCode_3315 leetCode_3315 = new LeetCode_3315();
        int[] res = leetCode_3315.minBitwiseArray(nums);
    }
}