package com.marks.leetcode.hotLC;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_347Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/10 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_347Test {

    @Test
    void topKFrequent() {
        // nums = [1,1,1,2,2,3], k = 2
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        LeetCode_347 leetCode_347 = new LeetCode_347();
        int[] ans = leetCode_347.topKFrequent(nums, k);
    }
}