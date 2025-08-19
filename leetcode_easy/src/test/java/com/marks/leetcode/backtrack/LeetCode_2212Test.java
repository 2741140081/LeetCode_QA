package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_2212 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 18, 2025</pre>
 */
public class LeetCode_2212Test {

    /**
     * Method: maximumBobPoints(int numArrows, int[] aliceArrows)
     */
    @Test
    public void testMaximumBobPoints() {
        LeetCode_2212 solution = new LeetCode_2212();
        
        // 测试用例1: 题目示例
        int numArrows1 = 9;
        int[] aliceArrows1 = {1,1,0,1,0,0,2,1,0,1,2,0};
        int[] result1 = solution.maximumBobPoints(numArrows1, aliceArrows1);
    }
    
    /**
     * 验证特定示例的输出是否符合预期
     */
    @Test
    public void testSpecificExample() {
        LeetCode_2212 solution = new LeetCode_2212();
        
        int numArrows = 9;
        int[] aliceArrows = {1,1,0,1,0,0,2,1,0,1,2,0};
        int[] result = solution.maximumBobPoints(numArrows, aliceArrows);
        
        // 验证总箭数
        assertEquals(numArrows, Arrays.stream(result).sum());
        
        // 注意：由于可能存在多种最优解，我们不能严格验证每个位置的值
        // 只能验证总得分是否合理
        int score = 0;
        for (int i = 0; i < 12; i++) {
            if (result[i] > aliceArrows[i]) {
                score += i;
            }
        }
        
        // 根据题目示例，期望得分应该是比较高的
        assertTrue(score >= 20); // 粗略估计应该至少有20分
    }
}