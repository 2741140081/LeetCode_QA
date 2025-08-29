package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class LeetCode_3211Test {

    private LeetCode_3211 solution;

    @BeforeEach
    void setUp() {
        solution = new LeetCode_3211();
    }

    @Test
    @DisplayName("测试 n = 1 的情况")
    void testValidStrings_N1() {
        List<String> result = solution.validStrings(1);
        Set<String> resultSet = new HashSet<>(result);
        Set<String> expected = new HashSet<>(Arrays.asList("0", "1"));
        assertEquals(expected, resultSet);
    }

    @Test
    @DisplayName("测试 n = 2 的情况")
    void testValidStrings_N2() {
        List<String> result = solution.validStrings(2);
        Set<String> resultSet = new HashSet<>(result);
        Set<String> expected = new HashSet<>(Arrays.asList("01", "10", "11"));
        assertEquals(expected, resultSet);
    }

    @Test
    @DisplayName("测试 n = 3 的情况")
    void testValidStrings_N3() {
        List<String> result = solution.validStrings(3);
        Set<String> resultSet = new HashSet<>(result);
        Set<String> expected = new HashSet<>(Arrays.asList("010", "011", "101", "110", "111"));
        assertEquals(expected, resultSet);
    }

    @Test
    @DisplayName("验证所有结果都是有效的")
    void testAllResultsAreValid() {
        for (int n = 1; n <= 5; n++) {
            List<String> result = solution.validStrings(n);
            for (String s : result) {
                assertTrue(isValidString(s), "字符串 " + s + " 不是有效的");
                assertEquals(n, s.length(), "字符串 " + s + " 长度不正确");
            }
        }
    }

    @Test
    @DisplayName("验证没有遗漏的有效字符串")
    void testNoValidStringsAreMissing() {
        for (int n = 1; n <= 4; n++) {
            List<String> result = solution.validStrings(n);
            Set<String> resultSet = new HashSet<>(result);

            // 生成所有可能的二进制字符串并检查哪些是有效的
            Set<String> expected = new HashSet<>();
            generateAllBinaryStrings(n, "", expected);

            assertEquals(expected, resultSet, "n=" + n + " 时结果不匹配");
        }
    }

    // 辅助方法：检查字符串是否有效
    private boolean isValidString(String s) {
        if (s.length() < 2) {
            return true;
        }
        for (int i = 0; i < s.length() - 1; i++) {
            String sub = s.substring(i, i + 2);
            if (!sub.contains("1")) {
                return false;
            }
        }
        return true;
    }

    // 辅助方法：生成所有可能的二进制字符串
    private void generateAllBinaryStrings(int n, String current, Set<String> result) {
        if (current.length() == n) {
            if (isValidString(current)) {
                result.add(current);
            }
            return;
        }

        generateAllBinaryStrings(n, current + "0", result);
        generateAllBinaryStrings(n, current + "1", result);
    }
}