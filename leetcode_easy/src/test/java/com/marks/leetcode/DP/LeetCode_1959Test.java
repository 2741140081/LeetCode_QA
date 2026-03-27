package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1959Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/26 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1959Test {

    @Test
    void minSpaceWastedKResizing() {
        // 输入：nums = [10,20,15,30,20], k = 2
        //输出：15
        int[] nums = {13,46,42,45,35};
        int k = 4;
        LeetCode_1959 leetCode_1959 = new LeetCode_1959();
        int res = leetCode_1959.minSpaceWastedKResizing(nums, k);
        System.out.println(res);
    }
}