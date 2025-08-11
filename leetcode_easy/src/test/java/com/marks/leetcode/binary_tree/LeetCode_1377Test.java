package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/1 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1377Test {

    @Test
    void frogPosition() {
        int n = 7,  t = 20, target = 6;
//        int[][] edges = {{1,2},{1,3},{1,7},{2,4},{2,6},{3,5}};
        int[][] edges = {{1,2},{1,3},{1,7},{2,4},{2,6},{3,5}};
        double result = new LeetCode_1377().frogPosition(n, edges, t, target);
        System.out.println("result ===>"+ result);
    }
}