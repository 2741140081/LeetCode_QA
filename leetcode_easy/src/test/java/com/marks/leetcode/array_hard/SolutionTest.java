package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SolutionTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/5 15:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class SolutionTest {

    @Test
    void pick() {
        // n = 7, blacklist = [2, 3, 5]
        int n = 7;
        int[] blacklist = {2,3,5};
        Solution solution = new Solution(n, blacklist);
        System.out.println(solution.pick());
    }
}