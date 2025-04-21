package com.marks.leetcode.data_structure.difference;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/21 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2251Test {

    @Test
    void fullBloomFlowers() {
        // flowers = {{1,6},{3,7},{9,12},{4,13}}, people = {2,3,7,11}
        int[][] flowers = {{1,6},{3,7},{9,12},{4,13}};
        int[] people = {2,11,3,7};
        int[] result = new LeetCode_2251().fullBloomFlowers(flowers, people);
    }
}