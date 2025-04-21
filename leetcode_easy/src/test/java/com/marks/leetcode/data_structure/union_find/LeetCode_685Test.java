package com.marks.leetcode.data_structure.union_find;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/14 17:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_685Test {

    @Test
    void findRedundantDirectedConnection() {
        int i = 12;
        int k = i & -i;
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toBinaryString(-i));
        // 0100 => 1100 & 0100
        i += i & -i;
        System.out.println(k);
        System.out.println(i);
    }
}