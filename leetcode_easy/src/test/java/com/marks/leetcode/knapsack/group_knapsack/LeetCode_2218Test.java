package com.marks.leetcode.knapsack.group_knapsack;

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
 * @data 2024/8/9 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2218Test {

    @Test
    void maxValueOfCoins() {
        // piles = [[100],[100],[100],[100],[100],[100],[1,1,1,1,1,1,700]], k = 7
        List<List<Integer>> piles = new ArrayList<>();
        piles.add(Arrays.asList(100));
        piles.add(Arrays.asList(100));
        piles.add(Arrays.asList(100));
        piles.add(Arrays.asList(100));
        piles.add(Arrays.asList(100));
        piles.add(Arrays.asList(100));
        piles.add(Arrays.asList(1,1,1,1,1,1,700));
        int k = 7;
        int result = new LeetCode_2218().maxValueOfCoins(piles, k);
        System.out.println(result);
    }
}