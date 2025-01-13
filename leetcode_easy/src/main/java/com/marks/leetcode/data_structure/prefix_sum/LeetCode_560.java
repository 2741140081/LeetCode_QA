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
 * @date 2025/1/13 15:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_560 {
    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
     *
     * 子数组是数组中元素的连续非空序列。
     * tips:
     * 1 <= nums.length <= 2 * 10^4
     * -1000 <= nums[i] <= 1000
     * -10^7 <= k <= 10^7
     *
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 15:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int subarraySum(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description: 
     * 查看官方题解
     * 前缀和 + 哈希表
     * [i, j]之前的和为
     * pre[j] - pre[i - 1] = k ==> pre[i - 1] = pre[j] - k, pre[i - 1] 是已经计算过了的, pre[j] 是当前preSum的值
     * preSum - k 是一个定值, 可以通过map.contains(key), 在O(1)的复杂度时间下查找
     * 所以这一步的转换很关键
     * ,
     * AC:24ms/45.45MB
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        int ans = 0, preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); // key为前缀和, value为数量
        for (int i = 0; i < n; i++) {
            preSum += nums[i];
            int key = preSum - k;
            if (map.containsKey(key)) {
                ans += map.get(key);
            }
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return ans;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [1,2,3], k = 3
     * 输出：2
     * 暴力试试看
     * 1957ms/44.73MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] preSum = new int[n];
        preSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = preSum[j] - preSum[i] + nums[i];
                if (sum == k) {
                    result++;
                }
            }
        }
        return result;
    }
}
