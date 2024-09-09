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
 * @data 2024/9/9 16:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1395Test {

    @Test
    void numTeams() {
        // [2,5,3,4,1]
        int[] rating = {2,5,3,4,1};
        int result = new LeetCode_1395().numTeams(rating);
        System.out.println(result);
    }
}