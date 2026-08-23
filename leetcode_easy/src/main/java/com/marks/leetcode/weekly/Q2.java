package com.marks.leetcode.weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q2 {

    public List<List<Integer>> findDisappearedNumbers(int[] nums, int lower, int upper) {
        List<List<Integer>> result;
        result = method_01(nums, lower, upper);
        return result;
    }

    private List<List<Integer>> method_01(int[] nums, int lower, int upper) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] > upper) {
                break;
            }
            if (nums[i] < lower) {
                continue;
            } else if (nums[i] == lower) {
                lower++;
                continue;
            }
            // nums[i] > lower && nums[i] <= upper
            if (nums[i] > lower) {
                List<Integer> curr = new ArrayList<>();
                curr.add(lower);
                curr.add(nums[i] - 1);
                ans.add(curr);
                lower = nums[i] + 1;
            }
        }
        // 处理 lower 与 upper
        if (lower <= upper) {
            List<Integer> curr = new ArrayList<>();
            curr.add(lower);
            curr.add(upper);
            ans.add(curr);
        }
        return ans;
    }

}
