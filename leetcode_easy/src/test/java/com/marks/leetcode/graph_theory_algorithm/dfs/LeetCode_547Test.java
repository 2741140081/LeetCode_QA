package com.marks.leetcode.graph_theory_algorithm.dfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_547Test {

    @Test
    void findCircleNum() {
        // isConnected = {{1,1,0},{1,1,0},{0,0,1}}
        int[][] isConnected = {{1,1,0},{1,1,0},{0,0,1}};
        int result = new LeetCode_547().findCircleNum(isConnected);
        System.out.println(result);
    }
}