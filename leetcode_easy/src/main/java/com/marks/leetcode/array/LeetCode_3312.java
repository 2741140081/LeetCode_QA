package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3312 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/17 9:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3312 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 和一个整数数组 queries 。
     * gcdPairs 表示数组 nums 中所有满足 0 <= i < j < n 的数对 (nums[i], nums[j]) 的 最大公约数 升序 排列构成的数组。
     * 对于每个查询 queries[i] ，你需要找到 gcdPairs 中下标为 queries[i] 的元素。
     * 请你返回一个整数数组 answer ，其中 answer[i] 是 gcdPairs[queries[i]] 的值。
     * gcd(a, b) 表示 a 和 b 的 最大公约数 。
     *
     * tips:
     * 2 <= n == nums.length <= 10^5
     * 1 <= nums[i] <= 5 * 10^4
     * 1 <= queries.length <= 10^5
     * 0 <= queries[i] < n * (n - 1) / 2
     * 1. 尝试计算具有公因数g的数对的数量
     * 2. 使用容斥原理。
     * @param: nums
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/17 9:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] gcdValues(int[] nums, long[] queries) {
        int[] result;
        result = method_01(nums, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 如果暴力求解 gcdPairs 时间复杂度是 O(n^2), 不可取会超时.
     * todo
     * @param: nums
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/17 9:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, long[] queries) {
        int m = 0;
        for (int num : nums) {
            m = Math.max(m, num); // 查找最大值
        }
        long[] cnt = new long[m + 1]; // 创建基于值域的数量数组
        for (int num : nums) {
            cnt[num]++;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = i * 2; j <= m; j += i) {
                cnt[i] += cnt[j];
            }
        }
        for (int i = 1; i <= m; i++) {
            cnt[i] = cnt[i] * (cnt[i] - 1) / 2;
        }
        for (int i = m; i >= 1; i--) {
            for (int j = i * 2; j <= m; j += i) {
                cnt[i] -= cnt[j];
            }
        }
        for (int i = 1; i <= m; i++) {
            cnt[i] += cnt[i - 1];
        }
        int[] ans = new int[queries.length];
        for (int k = 0; k < queries.length; k++) {
            long q = queries[k] + 1;
            int left = 1, right = m;
            while (left < right) {
                int mid = (left + right) / 2;
                if (cnt[mid] >= q) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            ans[k] = left;
        }
        return ans;
    }

}
