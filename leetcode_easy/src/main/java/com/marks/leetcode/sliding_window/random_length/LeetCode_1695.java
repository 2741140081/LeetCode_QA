package com.marks.leetcode.sliding_window.random_length;

import java.util.HashSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1695 {
    /**
     * @Description: [
     * 给你一个正整数数组 nums ，请你从中删除一个含有 若干不同元素 的子数组。删除子数组的 得分 就是子数组各元素之 和 。
     * 返回 只删除一个 子数组可获得的 最大得分 。
     * 如果数组 b 是数组 a 的一个连续子序列，即如果它等于 a[l],a[l+1],...,a[r] ，那么它就是 a 的一个子数组。
     * 
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^4
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumUniqueSubarray(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }
    /**
     * @Description: [
     * 输入：nums = [4,2,4,5,6]
     * 输出：17
     * 解释：最优子数组是 [2,4,5,6]
     *
     * AC:42ms/55.21MB
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // left 左边界
        int left = 0;
        // set集合保存窗口中的元素, 由于set集合特性
        HashSet<Integer> set = new HashSet<>();
        // sum记录窗口中的元素和
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int temp = nums[i];
            sum += temp;
            if (set.contains(temp)) {
                set.remove(temp);
                int pre = nums[left];
                sum = sum - nums[left];
                while (pre != temp) {
                    set.remove(pre);
                    left++;
                    pre = nums[left];
                    sum = sum - nums[left];
                }
                left++; // 设置left为下一个, 如果nums[left] == temp, 那么nums[left + 1] != temp.
            }
            set.add(temp);
            ans = Math.max(ans, sum);
        }
        return ans;
    }
}
