package com.marks.leetcode.weekly;

public class Q1 {

    public boolean isPalindromic(String s) {
        boolean result;
        result = method_01(s);
        return result;
    }

    private boolean method_01(String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            int curr = (int) c;
            // 将数字转出对应的二进制
            String binary = String.format("%8s", Integer.toBinaryString(curr)).replace(' ', '0');
            sb.append(binary);
        }
        if (sb.toString().equals(sb.reverse().toString())) {
            return true;
        }
        return false;
    }

}
