package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_3376的单元测试类
 */
class LeetCode_3376Test {

    private LeetCode_3376 leetCode3376;

    @BeforeEach
    void setUp() {
        leetCode3376 = new LeetCode_3376();
    }

    @Test
    void testFindMinimumTime_Example1() {
        // 测试示例1: strength = [3,4,1], K = 1
        // 预期输出: 4
        List<Integer> strength = Arrays.asList(3, 4, 1);
        int k = 1;
        int result = leetCode3376.findMinimumTime(strength, k);
        assertEquals(4, result, "对于strength=[3,4,1], K=1，期望结果为4");
    }

    @Test
    void testFindMinimumTime_SingleLock() {
        // 测试只有一个锁的情况
        List<Integer> strength = Collections.singletonList(5);
        int k = 2;
        int result = leetCode3376.findMinimumTime(strength, k);
        // X=1, 需要ceil(5/1)=5天
        assertEquals(5, result, "对于strength=[5], K=2，期望结果为5");
    }

    @Test
    void testFindMinimumTime_TwoLocks() {
        // 测试两个锁的情况
        List<Integer> strength = Arrays.asList(2, 3);
        int k = 1;
        int result = leetCode3376.findMinimumTime(strength, k);
        // 两种顺序:
        // 1. 先2后3: ceil(2/1) + ceil(3/2) = 2 + 2 = 4
        // 2. 先3后2: ceil(3/1) + ceil(2/2) = 3 + 1 = 4
        assertEquals(4, result, "对于strength=[2,3], K=1，期望结果为4");
    }

    @Test
    void testFindMinimumTime_LargeKValue() {
        // 测试较大的K值
        List<Integer> strength = Arrays.asList(10, 5, 2);
        int k = 5;
        int result = leetCode3376.findMinimumTime(strength, k);
        // 最优顺序应该是从小到大: 2, 5, 10
        // X=1: ceil(2/1) = 2天
        // X=6: ceil(5/6) = 1天
        // X=11: ceil(10/11) = 1天
        // 总计: 2 + 1 + 1 = 4天
        assertEquals(4, result, "对于strength=[10,5,2], K=5，期望结果为4");
    }

    @Test
    void testFindMinimumTime_IdenticalStrength() {
        // 测试所有锁强度相同的情况
        List<Integer> strength = Arrays.asList(3, 3, 3);
        int k = 1;
        int result = leetCode3376.findMinimumTime(strength, k);
        // 无论什么顺序，结果都相同:
        // ceil(3/1) + ceil(3/2) + ceil(3/3) = 3 + 2 + 1 = 6
        assertEquals(6, result, "对于strength=[3,3,3], K=1，期望结果为6");
    }

    @Test
    void testFindMinimumTime_LargeValues() {
        // 测试较大的数值
        List<Integer> strength = Arrays.asList(100, 200);
        int k = 1;
        int result = leetCode3376.findMinimumTime(strength, k);
        // 最优顺序: 100, 200
        // ceil(100/1) + ceil(200/2) = 100 + 100 = 200
        assertEquals(200, result, "对于strength=[100,200], K=1，期望结果为200");
    }

    @Test
    void testFindMinimumTime_FourLocks() {
        // 测试四个锁的情况
        List<Integer> strength = Arrays.asList(1, 2, 3, 4);
        int k = 2;
        int result = leetCode3376.findMinimumTime(strength, k);
        // 最优顺序应该是从小到大
        // X=1: ceil(1/1) = 1
        // X=3: ceil(2/3) = 1
        // X=5: ceil(3/5) = 1
        // X=7: ceil(4/7) = 1
        // 总计: 1 + 1 + 1 + 1 = 4
        assertEquals(4, result, "对于strength=[1,2,3,4], K=2，期望结果为4");
    }
}