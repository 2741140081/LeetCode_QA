package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1093Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1093Test {

    @Test
    void sampleStats() {
        int[] count = {0,4,3,2,2};
        LeetCode_1093 leetCode1093 = new LeetCode_1093();
        double[] doubles = leetCode1093.sampleStats(count);
    }
}