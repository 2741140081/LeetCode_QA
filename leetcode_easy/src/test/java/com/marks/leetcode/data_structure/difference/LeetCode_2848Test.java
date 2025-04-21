package com.marks.leetcode.data_structure.difference;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 16:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2848Test {

    @Test
    void numberOfPoints() {
        int[][] array = {{3,6},{1,5},{4,7}};
        List<List<Integer>> nums = Arrays.stream(array).map(ints -> Arrays.stream(ints).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        nums.forEach(System.out::println);
        int result = new LeetCode_2848().numberOfPoints(nums);
        System.out.println(result);
    }
}