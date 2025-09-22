package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/22 17:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2857Test {

    @Test
    void countPairs() {
        // coordinates = [[1,2],[4,2],[1,3],[5,2]], k = 5
        List<List<Integer>> coordinates = new ArrayList<>();
        coordinates.add(Arrays.asList(1, 2));
        coordinates.add(Arrays.asList(4, 2));
        coordinates.add(Arrays.asList(1, 3));
        coordinates.add(Arrays.asList(5, 2));

        LeetCode_2857 leetCode_2857 = new LeetCode_2857();
        int count = leetCode_2857.countPairs(coordinates, 5);
        System.out.println(count);
        assertEquals(2, count);
    }
}