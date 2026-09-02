package com.marks.leetcode.array_medium;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3824 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/2 9:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3824 {

    /**
     * @Description:
     * 给你一个 正 整数数组 nums。
     * 对于一个正整数 k，定义 nonPositive(nums, k) 为使 nums 的每个元素都变为 非正数 所需的 最小 操作 次数。
     * 在一次操作中，你可以选择一个下标 i 并将 nums[i] 减少 k。
     * 返回一个整数，表示满足 nonPositive(nums, k) <= k^2 的 k 的 最小 值。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/09/02 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumK(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 计算 nonPositive(nums, k) 需要的时间复杂度是 O(n),
     * 2. k 的取值范围是 [1, max(nums)], 不对, 如果 k >= max(nums[i]),此时最多操作 n 次, 需要 n <= k^2 所以 k >= sqrt(n) + 1,
     * 这两个值取较大值为 k 的取值范围是 [1, Math.max(sqrt(n) + 1, max(nums))]
     * 3. 将 nums[i] 减少到非正数所需的次数是: (nums[i] + (k - 1)) / k, sum of all times is the answer,
     * 这就相当于 ((nums[0] + nums[1] + ... + nums[n - 1]) + (k - 1) * n) / k, 不对, 这不是数学, 不能使用前缀和,
     * 因为每次除法的时候得到的是整数(小数被舍弃), 所以还是通过遍历得到结果是准确的
     * 4. 二分法查找 k 的最小值, 因为 k 越大 nonPositive(nums, k) 越小, 是一个递减的, 但是 k^2 是递增的, 所以可以通过二分法查找 k 的最小值,
     * AC: 63ms/129.23MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/09/02 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int maxNum = Arrays.stream(nums).max().getAsInt();
        int sqrtN = (int) Math.sqrt(n) + 1;
        int left = 1, right = Math.max(sqrtN, maxNum);
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nonPositive(nums, mid) <= (long) mid * mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private long nonPositive(int[] nums, int k) {
        long ans = 0;
        for (int num : nums) {
            ans += (num + k - 1) / k;
        }
        return ans;
    }

}
