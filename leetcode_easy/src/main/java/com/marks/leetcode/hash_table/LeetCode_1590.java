package com.marks.leetcode.hash_table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1590 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1590 {

    /**
     * @Description:
     * 给你一个正整数数组 nums，请你移除 最短 子数组（可以为 空），使得剩余元素的 和 能被 p 整除。 不允许 将整个数组都移除。
     * 请你返回你需要移除的最短子数组的长度，如果无法满足题目要求，返回 -1 。
     * 子数组 定义为原数组中连续的一组元素。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= p <= 10^9
     * @param: nums
     * @param: p
     * @return int
     * @author marks
     * @CreateDate: 2026/06/02 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSubarray(int[] nums, int p) {
        int result;
        result = method_01(nums, p);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [6,3,5,2], p = 9
     * 输出：2
     * 1. 先列出已知信息, int sum = Sum(nums); int mod = sum % p; 如果 mod = 0 则直接返回0
     * 2. mod != 0, 就需要找到一个子数组, 使得 sum(subNums) % p == mod, 并且 sum - subSum >= p
     * 3. 子数组的可能存在结果有3中, 一种是[0 ~ i], [j, k], [l, n - 1] 这三种结果取最短值
     * 4. [0 ~ i] 和 [l, n - 1] 可以通过前缀数组求出得到, 但是 [j, k] 这种无法直接得到结果
     * 5. 使用前缀和, 并且构建一个Map 保存 preSum % p 的结果 中 index 最大的结果
     * 6. 假设当前位于 i 处, int a = preSum[i] % p; mod 与 a 之间存在什么样的联系, 如何从map 中获取 b 使得 [j, k] 区间的成立
     * 7. 即 int a = preSum[j] % p; int b = preSum[k] % p;
     * error: 144/145, 解答错误, 发生了溢出, 将 int 修改为long
     * AC: 34ms/90.89MB
     * @param: nums
     * @param: p
     * @return int
     * @author marks
     * @CreateDate: 2026/06/02 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int p) {
        int n = nums.length;
        long sum = Arrays.stream(nums).asLongStream().sum();
        if (sum < p) {
            return -1;
        } else if (sum % p == 0) {
            return 0;
        }
        Map<Long, Integer> map = new HashMap<>();
        long mod = sum % p;
        int ans = Integer.MAX_VALUE;
        long[] prefixSum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
            long currMod = prefixSum[i] % p;
            long need = (currMod + p - mod) % p;
            if (map.containsKey(need)) {
                ans = Math.min(ans, i - map.get(need));
            }
            if (currMod == mod && (sum - prefixSum[i]) >= p) {
                ans = Math.min(ans, i);
            }
            map.put(currMod, i);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

}
