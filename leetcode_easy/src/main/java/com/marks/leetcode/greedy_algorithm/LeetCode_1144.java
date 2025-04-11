package com.marks.leetcode.greedy_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/9 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1144 {
    
    /**
     * @Description:
     * 给你一个整数数组 nums，每次 操作 会从中选择一个元素并 将该元素的值减少 1。
     *
     * 如果符合下列情况之一，则数组 A 就是 锯齿数组：
     *
     * 每个偶数索引对应的元素都大于相邻的元素，即 A[0] > A[1] < A[2] > A[3] < A[4] > ...
     * 或者，每个奇数索引对应的元素都大于相邻的元素，即 A[0] < A[1] > A[2] < A[3] > A[4] < ...
     * 返回将数组 nums 转换为锯齿数组所需的最小操作次数。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/4/9 11:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int movesToMakeZigzag(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 执行两次操作
     * 1. 偶数大
     * 2. 奇数大
     * n = 5
     * 0 ,1 ,2, 3, 4
     * n = 4
     * 0, 1, 2, 3
     * 最后判断所用最小
     *
     * 等下有点误解, 我之前用的是增加的, 但是题目要求是减少
     *
     * AC: 0ms/40.22MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/9 11:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 0;
        }
        int[] odd_nums = new int[n];
        System.arraycopy(nums, 0, odd_nums, 0, n);

        int even = 0, odd = 0;
        // even 偶数大
        int left = 2;
        if (nums[0] <= nums[1]) {
            even += (nums[1] - nums[0]) + 1;
            nums[1] = nums[0] - 1;
        }

        if (n % 2 == 1 && nums[n - 1] <= nums[n - 2]) {
            even += (nums[n - 2] - nums[n - 1]) + 1;
            nums[n - 2] = nums[n - 1] + 1;
        }
        while (left < n - 1) {
            if (nums[left - 1] >= nums[left]) {
                even += (nums[left - 1] - nums[left]) + 1;
                nums[left - 1] = nums[left] - 1;
            }
            if (nums[left + 1] >= nums[left]) {
                even += (nums[left + 1] - nums[left]) + 1;
                nums[left + 1] = nums[left] - 1;
            }
            left += 2;
        }

        // 奇数大
        left = 1;

        // 判断 n - 1 位置的奇偶
        if (n % 2 == 0 && odd_nums[n - 1] <= odd_nums[n - 2]) {
            odd += (odd_nums[n - 2] - odd_nums[n - 1]) + 1;
            odd_nums[n - 2] = odd_nums[n - 1] - 1;
        }

        while (left < n - 1) {
            if (odd_nums[left - 1] >= odd_nums[left]) {
                odd += (odd_nums[left - 1] - odd_nums[left]) + 1;
                odd_nums[left - 1] = odd_nums[left] - 1;
            }
            if (odd_nums[left + 1] >= odd_nums[left]) {
                odd += (odd_nums[left + 1] - odd_nums[left]) + 1;
                odd_nums[left + 1] = odd_nums[left] - 1;
            }

            left += 2;
        }

        return Math.min(even, odd);
    }
}
