package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1187Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/24 14:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1187Test {

    @Test
    void makeArrayIncreasing() {
        // 输入：arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
        int[] arr1 = {1,5,3,6,7};
        int[] arr2 = {1,3,2,4};
        LeetCode_1187 leetCode1187 = new LeetCode_1187();
        int result = leetCode1187.makeArrayIncreasing(arr1, arr2);
        System.out.println(result);
    }
}