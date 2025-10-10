package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_45Test {

    @Test
    void jump() {
        LeetCode_45 leetCode_45 = new LeetCode_45();
        int[] nums = {2,3,1,1,4};
        int result = leetCode_45.jump(nums);
        System.out.println(result);
        assertEquals(2, result);
    }
}