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
 * @data 2024/11/4 14:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1471Test {

    @Test
    void getStrongest() {
        // arr = [6,7,11,7,6,8], k = 5
        int[] arr = {6,7,11,7,6,8};
        int k = 5;
        int[] result = new LeetCode_1471().getStrongest(arr, k);
    }
}