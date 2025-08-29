package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_93的单元测试类
 */
class LeetCode_93Test {
    
    private LeetCode_93 leetCode93;
    
    @BeforeEach
    void setUp() {
        leetCode93 = new LeetCode_93();
    }
    
    @Test
    void testRestoreIpAddresses_Example1() {
        // 测试用例1: s = "1111"
        // 预期输出: ["1.1.1.1"]
        String s = "1111";
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertEquals(1, result.size());
        assertTrue(result.contains("1.1.1.1"));
    }
    
    @Test
    void testRestoreIpAddresses_Example2() {
        // 测试用例2: s = "101023"
        // 预期输出: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
        String s = "101023";
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertEquals(5, result.size());
        assertTrue(result.contains("1.0.10.23"));
        assertTrue(result.contains("1.0.102.3"));
        assertTrue(result.contains("10.1.0.23"));
        assertTrue(result.contains("10.10.2.3"));
        assertTrue(result.contains("101.0.2.3"));
    }
    
    @Test
    void testRestoreIpAddresses_Example3() {
        // 测试用例3: s = "11111111111111111111"
        // 预期输出: [] (因为长度超过12，无法组成有效的IP地址)
        String s = "11111111111111111111"; // 20个字符
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertEquals(0, result.size());
    }
    
    @Test
    void testRestoreIpAddresses_Example4() {
        // 测试用例4: s = "0000"
        // 预期输出: ["0.0.0.0"]
        String s = "0000";
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertEquals(1, result.size());
        assertTrue(result.contains("0.0.0.0"));
    }
    
    @Test
    void testRestoreIpAddresses_Example5() {
        // 测试用例5: s = "1111111111111"
        // 预期输出: [] (因为长度为13，超过12，无法组成有效的IP地址)
        String s = "1111111111111"; // 13个字符
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertEquals(0, result.size());
    }
    
    @Test
    void testRestoreIpAddresses_Example6() {
        // 测试用例6: s = "172162541"
        // 预期输出应包含 "17.216.25.41", "172.16.25.41"等合法组合
        String s = "172162541";
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertTrue(result.size() > 0);
        assertTrue(result.contains("17.216.25.41"));
        assertTrue(result.contains("172.16.25.41"));
    }
    
    @Test
    void testRestoreIpAddresses_InvalidWithLeadingZeros() {
        // 测试包含前导零的情况
        String s = "010010";
        List<String> result = leetCode93.restoreIpAddresses(s);
        // 有效的结果应该不包含像"01"这样的前导零
        assertFalse(result.contains("01.0.0.10")); // 无效IP，因为"01"有前导零
        assertTrue(result.contains("0.10.0.10")); // 有效IP
    }
    
    @Test
    void testRestoreIpAddresses_TooShort() {
        // 测试太短的字符串
        String s = "111"; // 少于4个字符
        List<String> result = leetCode93.restoreIpAddresses(s);
        assertEquals(0, result.size());
    }
    
    @Test
    void testIsValidMethod() {
        // 直接测试isValid方法的边界情况
        // 由于isValid是私有方法，我们通过公开接口间接测试
        
        // 测试带有前导零的情况
        List<String> result1 = leetCode93.restoreIpAddresses("010010");
        // 结果中不应包含前导零的IP段
        for (String ip : result1) {
            String[] segments = ip.split("\\.");
            for (String segment : segments) {
                if (segment.length() > 1) {
                    assertFalse(segment.startsWith("0"), "IP段不应包含前导零: " + segment);
                }
            }
        }
        
        // 测试超过255的数字
        List<String> result2 = leetCode93.restoreIpAddresses("999999999");
        // 结果中不应包含大于255的段
        for (String ip : result2) {
            String[] segments = ip.split("\\.");
            for (String segment : segments) {
                int value = Integer.parseInt(segment);
                assertTrue(value <= 255, "IP段值不应超过255: " + segment);
            }
        }
    }
}