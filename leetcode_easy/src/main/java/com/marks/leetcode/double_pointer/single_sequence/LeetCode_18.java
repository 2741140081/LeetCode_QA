package com.marks.leetcode.double_pointer.single_sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_18 {
    /**
     * @Description: [
     * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
     *
     * 0 <= a, b, c, d < n
     * a、b、c 和 d 互不相同
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     * 你可以按 任意顺序 返回答案 。
     *
     * tips:
     * 1 <= nums.length <= 200
     * -10^9 <= nums[i] <= 10^9
     * -10^9 <= target <= 10^9
     * ]
     * @param nums
     * @param target
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/11/5 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：arr = [1,0,-1,0,-2,2], target = 0
     * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
     * AC:103ms/44.49MB
     * ]
     * @param nums
     * @param target
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/11/5 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[] nums, int target) {
        int n = nums.length;
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
        }
        Arrays.sort(arr);
        ArrayList<List<Integer>> ans = new ArrayList<>();

        // a
        for (int i = 0; i < n - 3; i++) {
            long total = target - arr[i];
            // b
            for (int j = i + 1; j < n - 2; j++) {
                long temp = total - arr[j];
                int right = n - 1; // d

                // c
                for (int left = j + 1; left < n - 1; left++) {
                    while (right > left && arr[left] + arr[right] > temp) {
                        right--;
                    }
                    if (right == left) {
                        break;
                    }
                    if (arr[left] + arr[right] == temp) {
                        List<Integer> list = new ArrayList<>();
                        list.add((int) arr[i]);
                        list.add((int) arr[j]);
                        list.add((int) arr[left]);
                        list.add((int) arr[right]);
                        if (!ans.contains(list)) {
                            ans.add(list);
                        }
                    }
                }
            }
        }
        return ans;
    }
}
