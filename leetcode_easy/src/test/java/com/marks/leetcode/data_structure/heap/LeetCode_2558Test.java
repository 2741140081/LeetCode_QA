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
 * @date 2025/2/7 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2558Test {

    @Test
    void pickGifts() {
        int[] gifts = {25,64,9,4,100};
        int k = 4;
        long result = new LeetCode_2558().pickGifts(gifts, k);
        System.out.println(result);
    }
}