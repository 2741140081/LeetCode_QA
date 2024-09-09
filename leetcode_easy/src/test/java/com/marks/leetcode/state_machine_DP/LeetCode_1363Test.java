package com.marks.leetcode.state_machine_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/9 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1363Test {

    @Test
    void largestMultipleOfThree() {
        // 8, 1, 9
        // digits = [8,6,7,1,0]
//        int[] digits = {8, 1, 9};

        int[] digits = {9,7,6,7,6};
        String result = new LeetCode_1363().largestMultipleOfThree(digits);
        System.out.println(result);
    }
}