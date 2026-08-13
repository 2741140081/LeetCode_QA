package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3776Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 17:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3776Test {

    @Test
    void minMoves() {
        // balance = [1,2,-5,2]
        int[] nums = {1,2,-5,2};
        long result = new LeetCode_3776().minMoves(nums);
        System.out.println(result);
    }
}