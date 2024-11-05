package com.marks.leetcode.double_pointer.single_sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_611Test {

    @Test
    void triangleNumber() {
        // nums = [2,2,3,4]
        int[] nums = {2, 2, 3, 4};
        int result = new LeetCode_611().triangleNumber(nums);
        System.out.println(result);
    }
}