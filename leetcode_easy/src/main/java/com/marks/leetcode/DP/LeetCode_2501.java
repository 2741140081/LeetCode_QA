package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2501 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/24 14:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2501 {

    /**
     * @Description:
     * 给你一个整数数组 nums 。如果 nums 的子序列满足下述条件，则认为该子序列是一个 方波 ：
     *
     * 子序列的长度至少为 2 ，并且
     * 将子序列从小到大排序 之后 ，除第一个元素外，每个元素都是前一个元素的 平方 。
     * 返回 nums 中 最长方波 的长度，如果不存在 方波 则返回 -1 。
     *
     * 子序列 也是一个数组，可以由另一个数组删除一些或不删除元素且不改变剩余元素的顺序得到。
     * tips:
     * 2 <= nums.length <= 10^5
     * 2 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSquareStreak(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 子序列的顺序不重要, 只需要关注于子序列的长度(顺序会进行排序后能构成方波即可), 另外 nums[i] >= 2, 所以不需要特殊处理0, 1的情况
     * 2. 将数存储在Set集合中, 方便判断 x 的平方是否在集合中, 考虑最长的方波长度是多少? 通过 2 来计算得到 2 -> 4 -> 16 -> 256 -> 65536 最长方波个数是 5
     * AC: 37ms/110.59MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add((long) nums[i]);
        }

        int ans = -1;
        for (int i = 0; i < n; i++) {
            int count = 1;
            long num = nums[i];
            while (set.contains(num * num)) {
                count++;
                num *= num;
            }
            if (count > 1 && count > ans) {
                ans = count;
            }
            if (ans == 5) {
                return ans;
            }
        }
        return ans;
    }

}
