package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3971Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3971Test {

    @Test
    void maxTotalValue() {
        // 输入： value = [6,5,4], decay = [2,1,1], m = 4
        int[] value = {6,5,4};
        int[] decay = {2,1,1};
        int m = 4;

        // 输入： value = [6], decay = [4], m = 8
        value = new int[]{6};
        decay = new int[]{4};
        m = 8;

        // 输入： value = [9,9,7], decay = [1,5,2], m = 2
        value = new int[]{9,9,7};
        decay = new int[]{1,5,2};
        m = 2;

        int result = new LeetCode_3971().maxTotalValue(value, decay, m);
        System.out.println(result);
    }
}