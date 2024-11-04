package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_167 {
    public int[] twoSum(int[] numbers, int target) {
        int[] result;
        result = method_01(numbers, target);
        result = method_02(numbers, target);
        return result;
    }

    /**
     * @Description: [
     * 双指针
     * AC:1ms/46.00MB
     * ]
     * @param numbers
     * @param target
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/4 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] numbers, int target) {
        int n = numbers.length;
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[] {left + 1, right + 1};
            } else if (sum > target) {
                right--;
            }else {
                left++;
            }
        }
        return new int[] {-1, -1};
    }

    /**
     * @Description: [
     * 二分查找
     * AC:2ms/46.14MB
     * ]
     * @param numbers
     * @param target
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/4 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] numbers, int target) {
        int n = numbers.length;
        int key = 0;
        for (int left = 0; left < n - 1; left++) {
            key = target - numbers[left];
            int mid = Arrays.binarySearch(numbers, left + 1, n, key);
            if (mid > 0 && numbers[left] + numbers[mid] == target) {
                return new int[]{left + 1, mid + 1};
            }
        }

        return new int[0];
    }
}
