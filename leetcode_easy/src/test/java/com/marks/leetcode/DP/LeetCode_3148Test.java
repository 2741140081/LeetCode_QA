package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3148Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3148Test {

    @Test
    void maxScore() {
        LeetCode_3148 leetCode_3148 = new LeetCode_3148();
        // grid = [[9,5,7,3],[8,9,6,1],[6,7,14,3],[2,5,3,1]]
        List<List<Integer>> grid = new ArrayList<>();
        grid.add(Arrays.asList(9, 5, 7, 3));
        grid.add(Arrays.asList(8, 9, 6, 1));
        grid.add(Arrays.asList(6, 7, 14, 3));
        grid.add(Arrays.asList(2, 5, 3, 1));

        int result = leetCode_3148.maxScore(grid);


    }
}