package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3371Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 17:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3371Test {

    @Test
    void getLargestOutlier() {
        int[] nums = {6,-31,50,-35,41,37,-42,13};
        LeetCode_3371 leetCode_3371 = new LeetCode_3371();
        int result = leetCode_3371.getLargestOutlier(nums);
        System.out.println(result);
    }
}