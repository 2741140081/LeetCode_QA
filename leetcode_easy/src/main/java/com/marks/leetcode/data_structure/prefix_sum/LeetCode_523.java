package com.marks.leetcode.data_structure.prefix_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_523 {
    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k ，如果 nums 有一个 好的子数组 返回 true ，否则返回 false：
     * 一个 好的子数组 是：
     * 长度 至少为 2 ，且子数组元素总和为 k 的倍数。
     * 子数组 是数组中 连续 的部分。
     * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终 视为 k 的一个倍数。
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * 0 <= sum(nums[i]) <= 2^31 - 1
     * 1 <= k <= 2^31 - 1
     * @param nums 
     * @param k 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/13 16:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 暴力O(n^2)必定超时(TLE)
     * num[i, j] 是 k 的倍数 x
     * pre[j] - pre[i - 1] = k * x
     * pre[i - 1] = pre[j] - k * x
     * j - (i + 1) + 1 = j - i + 2
     * pre[j] - k * x >= 0
     * x = pre[j] / k
     * 少一个条件, 即子数组长度至少为2
     *
     * nums = [23,2,4,6,6], k = 7,
     * 23, 25, 29, 35
     *
     * AC:26ms/60.82MB
     * @param nums
     * @param k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/13 16:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int reminder = 0;
        for (int i = 0; i < n; i++) {
            reminder = (reminder + nums[i]) % k;
            if (map.containsKey(reminder)) {
                int prevIndex = map.get(reminder);
                if (i - prevIndex >= 2) {
                    return true;
                }
            } else {
                map.put(reminder, i);
            }
        }
        return false;
    }
}
