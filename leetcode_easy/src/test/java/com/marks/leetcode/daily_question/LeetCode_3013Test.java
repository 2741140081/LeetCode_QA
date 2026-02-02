package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3013Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/2 17:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3013Test {

    @Test
    void minimumCost() {
        // 输入：nums = [1,3,2,6,4,2], k = 3, dist = 3
        //输出：5

        // 输入：nums = [10,1,2,2,2,1], k = 4, dist = 3
        // 输出：15
        int[] nums = {10,1,2,2,2,1};
        int k = 4;
        int dist = 3;
        System.out.println(new LeetCode_3013().minimumCost(nums, k, dist));
    }
}