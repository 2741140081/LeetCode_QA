package com.marks.leetcode.BasicAlgorithm.merge_sort;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/24 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_912 {
    private int[] temp;
    /**
     * @Description:
     * 给你一个整数数组 nums，请你将该数组升序排列。
     *
     * 你必须在 不使用任何内置函数 的情况下解决问题，时间复杂度为 O(nlog(n))，并且空间复杂度尽可能小。
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/24 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sortArray(int[] nums) {
        Arrays.sort(nums);
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: 
     * 归并排序
     * @param nums 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/24 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        temp = new int[n];
        sort(nums, 0, n - 1);
        return nums;
    }

    private void sort(int[] nums, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = (right - left) / 2 + left;
        sort(nums, left, mid);
        sort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = nums[i];
        }

        int cnt = left;
        int i = left, j = mid + 1;
        while (i <= mid || j <= right) {
            if (i > mid) {
                nums[cnt++] = temp[j++];
            } else if (j > right) {
                nums[cnt++] = temp[i++];
            } else if (temp[i] <= temp[j]) {
                nums[cnt++] = temp[i++];
            } else {
                nums[cnt++] = temp[j++];
            }
        }
    }
}
