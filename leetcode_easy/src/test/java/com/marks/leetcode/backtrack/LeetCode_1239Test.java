package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_1239 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 18, 2025</pre>
 */
public class LeetCode_1239Test {

    /**
     * Method: maxLength(List<String> arr)
     */
    @Test
    public void testMaxLength() {
        LeetCode_1239 solution = new LeetCode_1239();
        
        // 测试用例1: 题目示例
        List<String> arr1 = Arrays.asList("cha", "r", "act", "ers");
        int expected1 = 6;
        assertEquals(expected1, solution.maxLength(arr1));
        
        // 测试用例2: 包含重复字符的字符串
        List<String> arr2 = Arrays.asList("un", "iq", "ue");
        int expected2 = 4;
        assertEquals(expected2, solution.maxLength(arr2));
        
        // 测试用例3: 空数组
        List<String> arr3 = Arrays.asList();
        int expected3 = 0;
        assertEquals(expected3, solution.maxLength(arr3));
        
        // 测试用例4: 单个字符串
        List<String> arr4 = Arrays.asList("abcdefghijklmnopqrstuvwxyz");
        int expected4 = 26;
        assertEquals(expected4, solution.maxLength(arr4));
        
        // 测试用例5: 包含自身有重复字符的字符串
        List<String> arr5 = Arrays.asList("aa", "bb");
        int expected5 = 0;
        assertEquals(expected5, solution.maxLength(arr5));
        
        // 测试用例6: 复杂情况
        List<String> arr6 = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p");
        int expected6 = 16;
        assertEquals(expected6, solution.maxLength(arr6));
        
        // 测试用例7: 字符串自身包含重复字符
        List<String> arr7 = Arrays.asList("abc", "def", "ghi", "ab");
        int expected7 = 9; // 可以选择"abc", "def", "ghi"得到长度9
        assertEquals(expected7, solution.maxLength(arr7));
    }
}