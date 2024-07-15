package com.marks.leetcode.array;

public class LeetCode_66 {
    public static void main(String[] args) {
        int[] digits = {1,2,3};
        int[] ints = plusOne(digits);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    private static int[] plusOne(int[] digits) {
        int flag = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            int temp = digits[i];
            if (i == digits.length-1) {
                temp = temp + 1;
            }
            temp = temp + flag;
            if (temp == 10) {
                digits[i] = 0;
                flag = 1;
            }else {
                flag = 0;
                digits[i] = temp;
            }
        }
        if (flag ==1) {
            int[] newDigits = new int[digits.length+1];
            newDigits[0] = flag;
            for (int i = 0; i < digits.length; i++) {
                newDigits[i+1] = digits[i];
            }
            return newDigits;
        }else {
            return digits;
        }
    }
}
