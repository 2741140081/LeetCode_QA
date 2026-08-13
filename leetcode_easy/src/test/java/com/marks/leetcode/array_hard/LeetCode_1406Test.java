package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1406Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/3 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1406Test {

    @Test
    void stoneGameIII() {
        // values = [1,2,3,6,1,2,3,6,5,1,2,3,6,8,1,2,3,6,-1,1,2,3,6,-5,1,2,3,6,-9,1,2,3,6,9]
        int[] values = {1,2,3,6,1,2,3,6,5,1,2,3,6,8,1,2,3,6,-1,1,2,3,6,-5,1,2,3,6,-9,1,2,3,6,9};
        LeetCode_1406 leetCode1406 = new LeetCode_1406();
        String result = leetCode1406.stoneGameIII(values);
        System.out.println(result);
    }
}