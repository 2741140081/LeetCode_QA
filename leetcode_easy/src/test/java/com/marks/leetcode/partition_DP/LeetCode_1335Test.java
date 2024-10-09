package com.marks.leetcode.partition_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 15:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1335Test {

    @Test
    void minDifficulty() {
        int[] jobDifficulty = {6,5,4,3,2,1};
        int d = 2;
        int result = new LeetCode_1335().minDifficulty(jobDifficulty, d);
        System.out.println(result);
    }
}