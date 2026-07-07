package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3698Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/2 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3698Test {

    @Test
    void splitArray() {
        // 输入： nums = [3,1,2]
        int[] nums = {3,1,2};
        long result = new LeetCode_3698().splitArray(nums);
        System.out.println(result);
    }
}