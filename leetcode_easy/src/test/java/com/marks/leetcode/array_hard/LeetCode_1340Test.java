package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1340Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/10 11:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1340Test {

    @Test
    void maxJumps() {
        // 输入：arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
        int[] arr = {22,29,52,97,29,75,78,2,92,70,90,12,43,17,97,18,58,100,41,32};
        int d = 17;
        LeetCode_1340 leetCode1340 = new LeetCode_1340();
        int result = leetCode1340.maxJumps(arr, d);
        System.out.println(result);
    }
}