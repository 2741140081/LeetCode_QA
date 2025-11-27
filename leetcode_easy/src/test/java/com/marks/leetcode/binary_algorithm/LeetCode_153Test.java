package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_153Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 17:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_153Test {

    @Test
    void findMin() {
        int[] nums = {4,5,6,7,0,1,2};
        LeetCode_153 leetCode_153 = new LeetCode_153();
        int result = leetCode_153.findMin(nums);
        System.out.println(result);
    }
}