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
 * @date 2024/12/6 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_907Test {

    @Test
    void sumSubarrayMins() {
        // arr = [3,1,2,4]
        int[] arr = {3,1,2,4};
        int result = new LeetCode_907().sumSubarrayMins(arr);
        System.out.println(result);
    }
}