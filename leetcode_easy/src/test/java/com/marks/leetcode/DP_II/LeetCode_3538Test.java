package com.marks.leetcode.DP_II;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3538Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/23 10:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3538Test {

    @Test
    void minTravelTime() {
        // l = 10, n = 4, k = 1, position = [0,3,8,10], time = [5,8,3,6]
        int l = 10;
        int n = 4;
        int k = 1;
        int[] position = {0,3,8,10};
        int[] time = {5,8,3,6};
        LeetCode_3538 leetCode_3538 = new LeetCode_3538();
        int res = leetCode_3538.minTravelTime(l, n, k, position, time);
        System.out.println(res);
    }
}