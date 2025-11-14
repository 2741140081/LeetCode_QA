package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2305Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2305Test {

    @Test
    void distributeCookies() {
        int[] cookies = {8,15,10,20,8};
        int k = 2;
        LeetCode_2305 leetCode_2305 = new LeetCode_2305();
        int result = leetCode_2305.distributeCookies(cookies, k);
        System.out.println(result);
    }
}