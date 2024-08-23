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
 * @data 2024/8/23 9:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_354Test {

    @Test
    void maxEnvelopes() {
        // envelopes = [[5,4],[6,4],[6,7],[2,3]]
        int[][] envelopes = {{5,4}, {6,4}, {6,7}, {2,3}};
        int result = new LeetCode_354().maxEnvelopes(envelopes);
        System.out.println(result);
    }
}