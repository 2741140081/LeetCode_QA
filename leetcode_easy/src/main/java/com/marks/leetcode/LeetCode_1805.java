package com.marks.leetcode;

import java.util.HashSet;
import java.util.Set;

public class LeetCode_1805 {
    public static void main(String[] args) {
//        String word = "a123bc34d008e8f34";
//        String word1 = "0000001110000000111";
//        String str = word1.replaceFirst("^0*","");
//
//        numDifferentInteger(word);

        Long sizeTemp = new Long(10)*1024*1024;
        System.out.println(sizeTemp);

    }

    private static int numDifferentInteger(String word) {
        if (word.length() < 1) {
            return 0;
        }
        Set<String> numList = new HashSet<>();
        char[] chars = word.toCharArray();
        String temp = "";
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 48 && chars[i] <=57) {
                temp += chars[i];
            }else {
                temp += ",";
            }
        }
        String[] strings = temp.split(",");

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].length() > 0) {
                String tempString = removePreZero(strings[i]);
                numList.add(tempString);
            }
        }

        return numList.size();
    }

    private static String removePreZero(String targetString) {
        int p2 = targetString.length();
        int p1 = 0;
        while (p2 - p1 >1 && targetString.charAt(p1) == '0') {
            p1++;
        }
        targetString = targetString.substring(p1,p2);
        return targetString;

    }
}
