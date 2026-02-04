package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_312Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/3 17:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_312Test {

    @Test
    void maxCoins() {
        // 输入：nums = [3,1,5,8]
        int[] nums = {3, 1, 5, 8};
        LeetCode_312 leetCode_312 = new LeetCode_312();
        int result = leetCode_312.maxCoins(nums);
        System.out.println(result);
    }
}