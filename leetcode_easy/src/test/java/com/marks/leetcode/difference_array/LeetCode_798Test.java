package com.marks.leetcode.difference_array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_798Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/11 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_798Test {

    @Test
    void bestRotation() {
        // 输入：nums = [2,3,1,4,0]
        int[] nums = {2,3,1,4,0};
        LeetCode_798 leetCode798 = new LeetCode_798();
        int result = leetCode798.bestRotation(nums);
    }
}