package com.marks.leetcode.double_pointer.single_sequence;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_658Test {

    @Test
    void findClosestElements() {
        // arr = [1,2,3,4,5], k = 4, x = 3
        int[] arr = {1,2,3,4,5};
        int k = 4;
        int x = 3;
        List<Integer> result = new LeetCode_658().findClosestElements(arr, k, x);
    }
}