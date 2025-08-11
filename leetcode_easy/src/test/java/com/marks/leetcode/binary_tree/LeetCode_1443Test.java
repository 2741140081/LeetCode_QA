package com.marks.leetcode.binary_tree;

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
 * @date 2025/7/30 16:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1443Test {

    @Test
    void minTime() {
//        int n = 7;
//        int[][] edges = {{0,1},{0,2},{1,4},{1,5},{2,3},{2,6}};
//        boolean[] hasAppleArray = {false,false,true,false,true,true,false};
        int n = 7;
        int[][] edges = {{0,1},{1,2},{1,3},{2,4},{4,5},{4,6}};
        boolean[] hasAppleArray = {false,true,false,false,true,false,true};
        List<Boolean> hasApple = new ArrayList<>();
        for (boolean b : hasAppleArray) {
            hasApple.add(b);
        }
        int result = new LeetCode_1443().minTime(n, edges, hasApple);
        System.out.println(result);
    }
}