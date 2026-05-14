package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1856Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/14 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1856Test {

    @Test
    void maxSumMinProduct() {
        // 输入：nums = [2,3,3,1,2]
        int[] nums = {2, 3, 3, 1, 2};
        LeetCode_1856 leetCode_1856 = new LeetCode_1856();
        int result = leetCode_1856.maxSumMinProduct(nums);
        System.out.println(result);
    }
}