package com.marks.leetcode.hotLC;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_136Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_136Test {

    @Test
    void singleNumber() {
        LeetCode_136 leetCode_136 = new LeetCode_136();
        // nums = [4,1,2,1,2]
        int[] nums = {4,1,2,1,2};
        int result = leetCode_136.singleNumber(nums);
    }
}