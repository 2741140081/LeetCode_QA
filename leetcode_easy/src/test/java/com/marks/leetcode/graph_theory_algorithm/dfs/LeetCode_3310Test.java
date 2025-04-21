package com.marks.leetcode.graph_theory_algorithm.dfs;

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
 * @date 2024/12/18 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3310Test {

    @Test
    void remainingMethods() {
        // 输入: n = 4, k = 1, invocations = {{1,2},{0,1},{3,2}}
        int n = 4, k = 1;
        int[][] invocations = {{1,2},{0,1},{3,2}};
        List<Integer> list = new LeetCode_3310().remainingMethods(n, k, invocations);
    }
}