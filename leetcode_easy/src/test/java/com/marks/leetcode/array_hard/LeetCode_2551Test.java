package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2551Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/20 16:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2551Test {

    @Test
    void putMarbles() {
        // 输入：weights = [1,3,5,1], k = 2
        int[] weights = {1,3,5,1};
        int k = 2;
        LeetCode_2551 leetCode2551 = new LeetCode_2551();
        long result = leetCode2551.putMarbles(weights, k);
        System.out.println(result);
    }
}