package com.marks.leetcode.monotonic_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 14:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_962Test {

    @Test
    void maxWidthRamp() {
        // [6,0,8,2,1,5]
        int[] nums  = {6,0,8,2,1,5};
        int result = new LeetCode_962().maxWidthRamp(nums);
        System.out.println(result);
    }
}