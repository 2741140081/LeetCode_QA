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
 * @data 2024/11/11 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1385Test {

    @Test
    void findTheDistanceValue() {
        // arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2
        int[] arr1 = {4,5,8};
        int[] arr2 = {10,9,1,8};
        int d = 2;
        int result = new LeetCode_1385().findTheDistanceValue(arr1, arr2, d);
    }
}