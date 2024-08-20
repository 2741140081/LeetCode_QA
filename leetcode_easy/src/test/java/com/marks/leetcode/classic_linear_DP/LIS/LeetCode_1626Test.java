package com.marks.leetcode.classic_linear_DP.LIS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/20 10:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1626Test {

    @Test
    void bestTeamScore() {
        // 输入：scores = [1,2,3,5], ages = [8,9,10,1]
        // 输入：scores = [4,5,6,5], ages = [2,1,2,1]
        int[] scores = {4, 5, 6, 5};
        int[] ages = {2,1,2,1};
        System.out.println(new LeetCode_1626().bestTeamScore(scores, ages));

        // [4,5,6,5]
    }
}