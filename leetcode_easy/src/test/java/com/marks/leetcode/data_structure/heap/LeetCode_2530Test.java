package com.marks.leetcode.data_structure.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2530Test {

    @Test
    void maxKelements() {
        int[] nums = {1,10,3,3,3};
        int k = 3;
        long result = new LeetCode_2530().maxKelements(nums, k);
        System.out.println(result);
    }
}