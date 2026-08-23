package com.marks.leetcode.weekly;

import java.util.*;

public class Q3 {

    public int longestSubarray(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    // 滑动窗口 + Map 集合
    // 如何找到一个数的质因数
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        // 记忆
        Map<Integer, Set<Integer>> memory = new HashMap<>();
        int ans = 0;
        int left = 0; // 左边界
        for (int right = 0; right < n; right++) {
            int num = nums[right];
            Set<Integer> primeFactors = getPrimeFactors(num);
            memory.put(right, primeFactors);
            for (int primeFactor : primeFactors) {
                map.merge(primeFactor, 1, Integer::sum);
            }
            while (map.size() > k) {
                Set<Integer> prev = memory.get(left);
                for (int primeFactor : prev) {
                    map.merge(primeFactor, -1, Integer::sum);
                    if (map.get(primeFactor) == 0) {
                        map.remove(primeFactor);
                    }
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    private Set<Integer> getPrimeFactors(int num) {
        Set<Integer> primeFactors = new HashSet<>();
        while (num % 2 == 0) {
            primeFactors.add(2);
            num /= 2;
        }
        for (int i = 3; i * i <= num; i += 2) {
            while (num % i == 0) {
                primeFactors.add(i);
                num /= i;
            }
        }
        if (num > 2) {
            primeFactors.add(num);
        }
        return primeFactors;
    }

}
