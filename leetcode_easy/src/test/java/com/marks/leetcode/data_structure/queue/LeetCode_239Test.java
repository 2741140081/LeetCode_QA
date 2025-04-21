package com.marks.leetcode.data_structure.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_239Test {

    @Test
    void maxSlidingWindow() {
        int[] nums = {7,2,4};
        int k = 2;
        int[] result = new LeetCode_239().maxSlidingWindow(nums, k);
    }
}