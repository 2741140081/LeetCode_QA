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
 * @data 2024/9/2 17:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2222Test {

    @Test
    void numberOfWays() {
        // s = "001101";
        // s = "11100"
        String s = "001101";
        long result = new LeetCode_2222().numberOfWays(s);
        System.out.println(result);
    }
}