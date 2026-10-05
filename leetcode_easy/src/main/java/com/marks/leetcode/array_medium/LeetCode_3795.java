package com.marks.leetcode.array_medium;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3795 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/14 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3795 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     * 返回一个 子数组 的 最小 长度，使得该子数组中出现的 不同 值之和（每个值只计算一次）至少 为 k。
     * 如果不存在这样的子数组，则返回 -1。
     * 子数组 是数组中一个连续的 非空 元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= 10^9
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/14 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minLength(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 滑动窗口解法
     * 2. 使用 Map 记录窗口中每个元素的出现次数, sum 记录窗口不同值之和
     * 3. 当 sum >= k 时, 更新结果, 并且此时需要移除窗口的左边界, 直到 sum < k
     * AC: 132ms/201.25MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/14 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int left = 0;
        int sum = 0;
        int ans = Integer.MAX_VALUE;
        Map<Integer, Integer> window  = new HashMap<>();
        for (int right = 0; right < n; right++) {
            int num = nums[right];
            if (!window.containsKey(num)) {
                sum += num;
                window.put(num, 1);
            } else {
                window.merge(num, 1, Integer::sum);
            }
            while (sum >= k) {
                ans = Math.min(ans, right - left + 1);
                window.merge(nums[left], -1, Integer::sum);
                if (window.get(nums[left]) == 0) {
                    window.remove(nums[left]);
                    sum -= nums[left];
                }
                left++;
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

}
