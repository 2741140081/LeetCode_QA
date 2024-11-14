package com.marks.leetcode.BasicAlgorithm;

public class AlgorithmCollect {
    public static void main(String[] args) {
        // 设置测试数据
    }

    /**
     * Time: 2023-11-17
     * FunctionName: 冒泡排序
     * Description: 将集合内的元素进行两两比较，若符合条件则交换元素位置
     *
     * @param nums
     * @return
     */
    public static int[] bubbleSorting(int[] nums) {

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i+1]) {
                int temp = nums[i+1];
                nums[i+1] = nums[i];
                nums[i] = temp;
            }
        }
        return nums;
    }

    /**
     * Time: 2023-11-17
     * FunctionName: 简单插入排序
     * Description: 将i+1的元素，与i+1之前的元素进行比较，
     * 若nums[i+1] > nums[i],
     * 若nums[i+1] < nums[i], 则交换元素，且再次循环比较i 和 i-1，直到遇到nums[m + 1] > nums[m]
     * @param nums
     * @return
     */
    public static int[] insertSortAsc(int[] nums) {
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {

            }
        }
        return nums;
    }

}
