package com.marks.leetcode.array;

public class LeetCode_58 {
    public static void main(String[] args) {
        String s = " one two a world ";
        System.out.println(lengthOfLastWord(s));
    }

    private static int lengthOfLastWord(String s) {
        String[] s1 = s.split(" ");

        return s1[s1.length-1].length();
    }
}
