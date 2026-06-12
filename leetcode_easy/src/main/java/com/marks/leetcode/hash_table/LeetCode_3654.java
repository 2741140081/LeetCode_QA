package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3654 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 15:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3654 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     * 你可以 多次 选择 连续 子数组 nums，其元素和可以被 k 整除，并将其删除；每次删除后，剩余元素会填补空缺。
     * 返回在执行任意次数此类删除操作后，nums 的最小可能 和。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= k <= 10^5
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/08 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minArraySum(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： nums = [3,1,4,1,5], k = 3
     * 1. 要使得删除后的数组和最小, 需要删除最大的元素和
     * 2. 通过前缀和 和 余数 求出最大值
     * 3. 构建动态规划, dp[i] 表示从 0 到 i 所剩余的数组和最小
     * 4. 由于删除连续数组不会影响余数的值
     * 5. [3,4,8,9,14] => [0, 1, 2, 0, 2]
     * 6. 使用 map 存储 余数及其对应的索引, 索引是最大的索引(最新的索引), 假设 i, j 的余数相同,
     * 那么根据动态规划 [i+1, j] 这一段可以被删除, dp[j] = dp[i] 或者 如果 map 中不存在对应的余数,
     * dp[i] = dp[i-1] + nums[i], 这两者取较小值
     * AC: 154ms/147.46MB
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/08 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        long[] preSum = new long[n];
        preSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        long[] dp = new long[n + 1];
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            dp[i + 1] = preSum[i];
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < n; i++) {
            int mod = (int) (preSum[i] % k);
            dp[i + 1] = Math.min(dp[i + 1], dp[i] + nums[i]);

            if (map.containsKey(mod)) {
                int prevIndex = map.get(mod);
                dp[i + 1] = Math.min(dp[prevIndex + 1], dp[i + 1]);
            }
            map.put(mod, i);
        }
        return dp[n];
    }

}
