package com.marks.leetcode.classic_linear_DP.LIS;

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
 * @data 2024/8/23 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2407 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k 。
     *
     * 找到 nums 中满足以下要求的最长子序列：
     *
     * 子序列 严格递增
     * 子序列中相邻元素的差值 不超过 k 。
     * 请你返回满足上述要求的 最长子序列 的长度。
     *
     * 子序列 是从一个数组中删除部分元素后，剩余元素不改变顺序得到的数组。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i], k <= 10^5
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lengthOfLIS(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        int result_2 = method_02(nums, k);
        return result == result_2 ? result : result_2;
    }

    /**
     * @Description: [
     * 贪心 + 二分法
     * 错误
     * 错误案例:nums = [1,4,7,15,5], k = 1
     * method_02 返回结果为1, 实际结果为2
     *
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        if (n == 1) {
            return 1;
        }
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);
        for (int i = 1; i < n; i++) {
            int num = nums[i];
            if (num > list.get(list.size() - 1) && num - list.get(list.size() - 1) <= k) {
                list.add(num);
            }else {
                // 二分法 查找插入位置
                int index = binarySearch(list, num);
                if (index == list.size() - 1) {
                    // 如果替换最后一个
                    if (list.size() == 1) {
                        list.set(index, num);
                    }else if (list.size() >= 2){
                        if (num - list.get(index - 1) <= 3) {
                            list.set(index, num);
                        }
                    }
                } else if (index == 0) {
                    // 如果替换第一个
                    if (list.size() == 1) {
                        list.set(index, num);
                    }else if (list.size() >= 2){
                        if (list.get(index + 1) - num <= 3 ) {
                            list.set(index, num);
                        }
                    }
                } else if (list.size() >= 3) {
                    if (list.get(index + 1) - num <= 3 && num - list.get(index - 1) <= 3) {
                        list.set(index, num);
                    }
                }
            }
        }
        return list.size();
    }

    private int binarySearch(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (list.get(mid) < target) {
                left = mid + 1;
            }else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [4,2,1,4,3,4,5,8,15], k = 3
     * 输出：5
     * 解释：
     * 满足要求的最长子序列是 [1,3,4,5,8] 。
     * 子序列长度为 5 ，所以我们返回 5 。
     * 注意子序列 [1,3,4,5,8,15] 不满足要求，因为 15 - 8 = 7 大于 3 。
     *
     * 果然不出意外的超时, 由于数量级是10^5， 所以O^2的时间复杂度大概率超时
     * 改进成贪心 + 二分法 见 method_02()
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 15:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        // 由于数量级是10^5， 所以O^2的时间复杂度大概率超时
        // 看看我能不能自己写一个贪心 + 二分法
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && nums[i] - nums[j] <= k) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
