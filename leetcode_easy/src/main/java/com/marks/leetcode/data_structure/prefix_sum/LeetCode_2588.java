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
 * @date 2025/1/14 10:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2588 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组nums 。每次操作中，你可以：
     *
     * 选择两个满足 0 <= i, j < nums.length 的不同下标 i 和 j 。
     * 选择一个非负整数 k ，满足 nums[i] 和 nums[j] 在二进制下的第 k 位（下标编号从 0 开始）是 1 。
     * 将 nums[i] 和 nums[j] 都减去 2k 。
     * 如果一个子数组内执行上述操作若干次后，该子数组可以变成一个全为 0 的数组，那么我们称它是一个 美丽 的子数组。
     *
     * 请你返回数组 nums 中 美丽子数组 的数目。
     *
     * 子数组是一个数组中一段连续 非空 的元素序列。
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^6
     * @param nums 
     * @return long
     * @author marks
     * @CreateDate: 2025/1/14 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long beautifulSubarrays(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }
    
    /**
     * @Description:
     * 输入：nums = [4,3,1,2,4]
     * 输出：2
     * [4, 7, 8, 10, 14]
     * pre[i] 是前i个元素和
     * pre[i ~ j] 是j ~ i + 1 的元素和
     * 1. pre[i] = pre[j] - pre[i]
     * 2. pre[i] - pre[k] = pre[j] - pre[i]
     * => 2 * pre[i] = pre[j] + pre[k]
     *
     * AC:53ms/55.30MB
     * @param nums 
     * @return long
     * @author marks
     * @CreateDate: 2025/1/14 10:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        long ans = 0;
        int s = 0;
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        cnt.put(s, 1); // s[0]
        for (int x : nums) {
            s ^= x;
            ans += cnt.getOrDefault(s, 0);
            cnt.merge(s, 1, Integer::sum);
        }
        return ans;
    }
}
