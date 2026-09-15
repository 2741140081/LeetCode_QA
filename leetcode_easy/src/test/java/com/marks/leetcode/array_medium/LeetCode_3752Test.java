package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3752Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/15 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3752Test {

    @Test
    void lexSmallestNegatedPerm() {
        int n = 3;
        long target = -2;
        LeetCode_3752 leetCode3752 = new LeetCode_3752();
        int[] result = leetCode3752.lexSmallestNegatedPerm(n, target);
        System.out.println(Arrays.toString(result));
    }
}