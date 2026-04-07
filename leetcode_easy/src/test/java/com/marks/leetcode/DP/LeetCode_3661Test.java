package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3661Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/7 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3661Test {

    @Test
    void maxWalls() {
        // 输入: robots = [10,2], distance = [5,1], walls = [5,2,7]
        int[] robots = {10, 2};
        int[] distance = {5, 1};
        int[] walls = {5, 2, 7};
        LeetCode_3661 leetCode_3661 = new LeetCode_3661();
//        int ans = leetCode_3661.maxWalls(robots, distance, walls);
//        System.out.println(ans);

        // 输入: robots = [1,2], distance = [100,1], walls = [10]
        int[] robots1 = {17,59,32,11,72,18};
        int[] distance1 = {5,7,6,5,2,10};
        int[] walls1 = {17,25,33,29,54,53,18,35,39,37,20,14,34,13,16,58,22,51,56,27,10,15,12,23,45,43,21,2,42,7,32,40,8,9,1,5,55,30,38,4,3,31,36,41,57,28,11,49,26,19,50,52,6,47,46,44,24,48};
        int ans1 = leetCode_3661.maxWalls(robots1, distance1, walls1);
        System.out.println(ans1);
    }
}