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
 * @data 2024/11/8 15:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_80Test {

    @Test
    void removeDuplicates() {
        // nums = [0,0,1,1,1,1,2,3,3]
        int[] nums = {0,0,1,1,1,1,2,3,3};
        int result = new LeetCode_80().removeDuplicates(nums);
        System.out.println(result);
    }
}