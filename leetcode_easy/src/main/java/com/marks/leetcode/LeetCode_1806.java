package com.marks.leetcode;

import java.util.Arrays;

public class LeetCode_1806 {
    public static void main(String[] args) {
        int n = 8;
        int i = reinitializePermutation(n);
        System.out.println("需要经过步数===>" + i);
    }

    private static int reinitializePermutation(int n) {
        int[] perm = new int[n];
        int[] original = new int[n];
        for (int i = 0; i < perm.length; i++) {
            perm[i] = i;
            original[i] = i;
        }

        perm = permStep(perm);
        int sum = 1;
        while (!Arrays.equals(perm, original)) {
            perm = permStep(perm);
            sum++;
        }
        return sum;
    }

    private static int[] permStep(int[] perm) {
        int n = perm.length;
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                arr[i] = perm[i/2];
            }else {
                arr[i] = perm[n/2 + (i-1)/2];
            }
        }
        perm = arr;
        for (int i = 0; i < perm.length; i++) {
            System.out.println("perm["+i+"]的值"+perm[i]);
        }
        return perm;
    }
}
