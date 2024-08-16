package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 17:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3152Test {

    @Test
    void isArraySpecial() {
        // nums = [4,3,1,6], queries = [[0,2],[2,3]]
        int[] nums = {4, 3, 1, 6};
        int[][] queries = new int[][]{{0, 2}, {2, 3}};
        System.out.println(new LeetCode_3152().isArraySpecial(nums, queries));
    }
}