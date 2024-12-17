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
 * @date 2024/12/17 17:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_797Test {

    @Test
    void allPathsSourceTarget() {
        int[][] graph = {{4,3,1},{3,2,4},{3},{4},{}};
        List<List<Integer>> result = new LeetCode_797().allPathsSourceTarget(graph);
        System.out.println(result.size());
    }
}