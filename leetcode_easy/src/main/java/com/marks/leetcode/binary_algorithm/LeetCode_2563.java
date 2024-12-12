package com.marks.leetcode.binary_algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/19 9:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2563 {

    /**
     * @Description: [
     * 给你一个下标从 0 开始、长度为 n 的整数数组 nums ，和两个整数 lower 和 upper ，返回 公平数对的数目 。
     *
     * 如果 (i, j) 数对满足以下情况，则认为它是一个 公平数对 ：
     *
     * 0 <= i < j < n，且
     * lower <= nums[i] + nums[j] <= upper
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * nums.length == n
     * -10^9 <= nums[i] <= 10^9
     * -10^9 <= lower <= upper <= 10^9
     * ]
     * @param nums 
     * @param lower 
     * @param upper
     * @return long
     * @author marks
     * @CreateDate: 2024/11/19 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countFairPairs(int[] nums, int lower, int upper) {
        long result;
        result = method_01(nums, lower, upper);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [0,1,7,4,4,5], lower = 3, upper = 6
     * 输出：6
     * 解释：共计 6 个公平数对：(0,3)、(0,4)、(0,5)、(1,3)、(1,4) 和 (1,5) 。
     *
     * 超时:47/54
     * ]
     * @param nums
     * @param lower
     * @param upper
     * @return long
     * @author marks
     * @CreateDate: 2024/11/19 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int lower, int upper) {
        long ans = 0;
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);
        // n >= 2, i相当于nums[j]
        for (int i = 1; i < n; i++) {
            // i < n - 1, i 的取值为[0 ~ n - 2]
            int target_lower = lower - nums[i];
            int target_upper = upper - nums[i];
            Collections.sort(list);
            long left = binarySearchLower(list, target_lower);
            long right = binarySearchUpper(list, target_upper);
            if (right >= left) {
                ans += right - left;
            }
            list.add(nums[i]);
        }
        return ans;
    }
    /**
     * @Description: [
     * nums[i] + nums[j] >= lower
     * list = {0, 1, 7} target_lower = lower - nums[3] = 3 - 4 = -1
     * left = 0, right = 2, mid = 1, list.get(1) = 1 > -1
     * left = 0, right = 0, mid = 0, list.get(0) = 0 > -1
     * left = 0, right = -1, break
     *
     * ]
     * @param list
     * @param target_lower
     * @return long
     * @author marks
     * @CreateDate: 2024/11/19 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long binarySearchLower(List<Integer> list, int target_lower) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (list.get(mid) < target_lower) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * @Description: [
     * list = {0, 1, 4, 4, 7} target_upper = upper - nums[5] = 6 - 5 = 1
     * left = 0, right = 5, mid = 2, list.get(2) = 4 > 1
     * left = 0, right = 1, mid = 0, list.get(0) = 0 < 1
     * left = 1, right = 1, mid = 1, list.get(1) = 1 = 1
     * left = 2, right = 1, break
     * ]
     * @param list
     * @param target_upper
     * @return long
     * @author marks
     * @CreateDate: 2024/11/19 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long binarySearchUpper(List<Integer> list, int target_upper) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (list.get(mid) > target_upper) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}
