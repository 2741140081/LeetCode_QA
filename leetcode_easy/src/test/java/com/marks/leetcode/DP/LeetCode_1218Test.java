package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1218Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1218Test {

    @Test
    void longestSubsequence() {
        // 输入：arr = [1,5,7,8,5,3,4,2,1], difference = -2
        int[] arr = {1,5,7,8,5,3,4,2,1};
        int difference = -2;
        int result = new LeetCode_1218().longestSubsequence(arr, difference);
        System.out.println(result);
    }
}