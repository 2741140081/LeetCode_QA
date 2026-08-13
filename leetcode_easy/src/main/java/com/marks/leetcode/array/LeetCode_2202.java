package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2202 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/25 17:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2202 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums ，它表示一个 堆 ，其中 nums[0] 是堆顶的元素。
     * 每一次操作中，你可以执行以下操作 之一 ：
     * 如果堆非空，那么 删除 堆顶端的元素。
     * 如果存在 1 个或者多个被删除的元素，你可以从它们中选择任何一个，添加 回堆顶，这个元素成为新的堆顶元素。
     * 同时给你一个整数 k ，它表示你总共需要执行操作的次数。
     * 请你返回 恰好 执行 k 次操作以后，堆顶元素的 最大值 。如果执行完 k 次操作以后，堆一定为空，请你返回 -1 。
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i], k <= 10^9
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/25 17:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumTop(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 当 n > 1 时, k > n + 1, return max; max 为 nums[] 中最大的元素
     * 2.  k < n + 1; 假设最大元素下标是 j, 删除前 [0 ~ j - 1] 需要执行 j 次操作, 剩余 k - j 次操作
     * AC: 5ms/71.01MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/06/25 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        if (n == 1) {
            // 只有1个元素, 并且 k 是奇数, 由于只有偶数才能向堆中添加元素, 所以奇数只能删除元素, 堆中无元素, 返回-1
            return k % 2 == 1 ? -1 : nums[0];
        }
        int max = Arrays.stream(nums).max().getAsInt();
        if (k > n + 1) {
            return max;
        }
        int ans = 0;
        for (int i = 0; i < k - 1; i++) {
            if (i < n) {
                ans = Math.max(ans, nums[i]);
            }
        }
        // 剩余1次操作, 可以将max 添加 或者删除当前 nums[k], 保留 nums[k + 1]
        if (k < n - 1) {
            ans = Math.max(ans, nums[k + 1]);
        }

        return ans;
    }

}
