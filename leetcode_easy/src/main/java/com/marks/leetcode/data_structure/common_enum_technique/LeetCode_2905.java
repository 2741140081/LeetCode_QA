package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/10 10:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2905 {
    /**
     * @Description:
     * 给你一个下标从 0 开始、长度为 n 的整数数组 nums ，以及整数 indexDifference 和整数 valueDifference 。
     *
     * 你的任务是从范围 [0, n - 1] 内找出  2 个满足下述所有条件的下标 i 和 j ：
     * abs(i - j) >= indexDifference 且
     * abs(nums[i] - nums[j]) >= valueDifference
     * 返回整数数组 answer。如果存在满足题目要求的两个下标，则 answer = [i, j] ；否则，answer = [-1, -1] 。如果存在多组可供选择的下标对，只需要返回其中任意一组即可。
     *
     * 注意：i 和 j 可能 相等 。
     * 1 <= n == nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * 0 <= indexDifference <= 10^5
     * 0 <= valueDifference <= 10^9
     * @param nums 
     * @param indexDifference 
     * @param valueDifference
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/10 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        int[] result;
        result = method_01(nums, indexDifference, valueDifference);
        result = method_02(nums, indexDifference, valueDifference);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/61.73MB
     * @param nums
     * @param indexDifference
     * @param valueDifference
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/10 10:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums, int indexDifference, int valueDifference) {
        int maxIdx = 0;
        int minIdx = 0;
        for (int j = indexDifference; j < nums.length; j++) {
            int i = j - indexDifference;
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            } else if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }
            if (nums[maxIdx] - nums[j] >= valueDifference) {
                return new int[]{maxIdx, j};
            }
            if (nums[j] - nums[minIdx] >= valueDifference) {
                return new int[]{minIdx, j};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * @Description:
     * 输入：nums = [5,1,4,1], indexDifference = 2, valueDifference = 4
     * 输出：[0,3]
     * 即找到一组下标, 使得i, j 相距最远, nums[i], nums[j] 差值最大
     * 1. 找到最小值与最大值的差值,
     * WA: 解答错误 515 / 517
     * @param nums
     * @param indexDifference
     * @param valueDifference
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/10 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int indexDifference, int valueDifference) {
        int minValue = Arrays.stream(nums).min().getAsInt();
        int maxValue = Arrays.stream(nums).max().getAsInt();
        int n = nums.length;
        if (Math.abs(maxValue - minValue) < valueDifference || n - 1 < indexDifference) {
            return new int[] {-1, -1};
        }
        int left = 0;
        int right = indexDifference;
        int[] ans = new int[] {-1, -1};
        int max_left = -1;
        int max_left_index = -1;

        int min_left = Integer.MAX_VALUE;
        int min_left_index = -1;

        int max_right = -1;
        int max_right_index = -1;

        int min_right = Integer.MAX_VALUE;
        int min_right_index = -1;

        while (right < n) {
            if (nums[left] > max_left) {
                max_left = nums[left];
                max_left_index = left;
            }

            if (nums[right] > max_right) {
                max_right = nums[right];
                max_right_index = right;
            }

            if (nums[left] < min_left) {
                min_left = nums[left];
                min_left_index = left;
            }

            if (nums[right] < min_right) {
                min_right = nums[right];
                min_right_index = right;
            }

            if (Math.abs(max_left - min_right) >= valueDifference && Math.abs(max_left_index - min_right_index) >= indexDifference) {
                ans[0] = max_left_index;
                ans[1] = min_right_index;
                return ans;
            }

            if (Math.abs(min_left - max_right) >= valueDifference && Math.abs(min_left_index - max_right_index) >= indexDifference) {
                ans[0] = min_left_index;
                ans[1] = max_right_index;
                return ans;
            }
            left++;
            right++;
        }
        return ans;
    }
}
