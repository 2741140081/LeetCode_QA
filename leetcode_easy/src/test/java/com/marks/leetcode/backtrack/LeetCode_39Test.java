package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/21 10:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_39Test {

    @Test
    void combinationSum() {
        LeetCode_39 leetCode_39 = new LeetCode_39();
        int[] candidates = {2,3,6,7};
        int target = 7;
        List<List<Integer>> result = leetCode_39.combinationSum(candidates, target);

        int[] candidates2 = {2,3,5};
        int target2 = 8;
        List<List<Integer>> result2 = leetCode_39.combinationSum(candidates2, target2);
    }
}