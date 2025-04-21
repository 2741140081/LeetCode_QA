package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/8 9:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2815 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums 。
     * 请你从 nums 中找出和 最大 的一对数，且这两个数数位上最大的数字相等。
     *
     * 返回最大和，如果不存在满足题意的数字对，返回 -1 。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 9:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSum(int[] nums) {
        int result;
        result = method_01(nums); // 双重for 循环遍历, 找到最大数位相同的值 O(n^2)
        result = method_02(nums); // 二维list数组存放数位相同的值, 并且倒序排序 O(n * logn)
        result = method_03(nums); // 一维数组存放数位最大值 O(n)
        return result;
    }

    /**
     * @Description: 最佳方案AC:2ms/43.51MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums) {
        int[] preNum = new int[10]; // 存放数位maxValue的最大的nums[i]
        int ans = -1;
        for (int i = 0; i < nums.length; i++) {
            int maxValue = getMaxValue(nums[i]);
            if (preNum[maxValue] == 0) {
                // 存放该数位的第一个值
                preNum[maxValue] = nums[i];
                continue;
            } else {
                // 如果当前数位已经存放了一个值, 就相当于两个数最大数位相同
                ans = Math.max(ans, nums[i] + preNum[maxValue]);
                preNum[maxValue] = Math.max(nums[i], preNum[maxValue]);
            }
        }
        return ans;
    }

    /**
     * @Description: 遍历nums[], 找打每一个对象nums[i]的数位最大值,
     * AC:6ms/44.00MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        List<Integer>[] lists = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int num : nums) {
            int maxValue = getMaxValue(num);
            lists[maxValue].add(num);
        }
        int ans = -1;
        for (List<Integer> list : lists) {
            if (list.size() >= 2) {
                Collections.sort(list, Collections.reverseOrder());
                ans = Math.max(ans, list.get(0) + list.get(1));
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 枚举left, 找right
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 9:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = -1;
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            int maxValue = getMaxValue(nums[i]);
            for (int j = i + 1; j < n; j++) {
                if (maxValue == getMaxValue(nums[j])) {
                    ans = Math.max(ans, nums[i] + nums[j]);
                }
            }
        }
        return ans;
    }

    /**
     * @Description: 1 <= num <= 10^4
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int getMaxValue(int num) {
        int max = 0;
        int mod = 10;
        while (num > 0) {
            max = Math.max(max, num % mod);
            num = num / 10;
        }
        return max;
    }
}
