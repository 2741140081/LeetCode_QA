package com.marks.leetcode.data_structure.binary_indexed_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/19 15:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCR_170Test {

    @Test
    void reversePairs() {
//        int[] record = {9, 7, 5, 4, 6};
        int[] record = {1,3,2,3,1};
        int result = new LCR_170().reversePairs(record);
        System.out.println(result);
    }
}