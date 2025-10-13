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
 * @date 2025/10/11 11:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_09Test {

    @Test
    void minJump() {
        int[] jump = {2, 5, 1, 1, 1, 1};
        LCP_09 lcp_09 = new LCP_09();
        int result = lcp_09.minJump(jump);
        System.out.println(result);
        assertEquals(3, result);
    }
}