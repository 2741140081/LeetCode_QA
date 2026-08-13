package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3698 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/2 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3698 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 将数组 恰好 分成两个子数组 left 和 right ，使得 left 严格递增 ，right 严格递减 。
     * 返回 left 与 right 的元素和之间 绝对差值的最小可能值 。如果不存在有效的分割方案，则返回 -1 。
     * 子数组 是数组中连续的非空元素序列。
     * 当数组中每个元素都严格大于其前一个元素（如果存在）时，称该数组为严格递增。
     * 当数组中每个元素都严格小于其前一个元素（如果存在）时，称该数组为严格递减。
     *
     * tips:
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/02 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long splitArray(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 由于 left 和 right 必须是非空元素序列, 所以有3种数组可以满足 left 递增 right 递减要求
     * 2. 除第一个元素外, [1, n - 1] 是严格递减数组, 此时 left 是 [0], right 是[1, n - 1]
     * 3. 除最后一个元素外, [0, n - 2] 是严格递增的, 构成 left, [n - 1] 构成 right;
     * 4. 数组是一个山形数组, 山顶为 i, 并且可以有1个或者2个相邻的峰顶元素, 如果有两个相邻的且相等的峰顶,
     * [0, i] 组成 left, [i + 1, n - 1] 组成right; 如果只有一个峰顶, [0, i - 1] 组成 left, [i + 1, n - 1] 组成 right,
     * nums[i] 则添加到 sum 值更小的数组种, 使得绝对差值更小.
     * 5. 其它情况返回 -1.
     * 6. 构建一个 boolean[] isIncrease, 判断 i 是否是严格递增, boolean[] isDecrease, 递减.
     * AC: 11ms/119.84MB
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/02 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        if (n == 2) {
            return Math.abs(nums[0] - nums[1]);
        }
        // n > 2
        long[] preSum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        boolean[] isIncrease = new boolean[n];
        isIncrease[0] = true;
        int left = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1] && isIncrease[i - 1]) {
                isIncrease[i] = true;
                left = i;
            }
        }
        boolean[] isDecrease = new boolean[n];
        isDecrease[n - 1] = true;
        int right = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1] && isDecrease[i + 1]) {
                isDecrease[i] = true;
                right = i;
            }
        }
        if (right > left + 1) { // 中间剩余数量 > 1
            return -1;
        } else if (right == left + 1) {
            long sumLeft = preSum[left + 1];
            long sumRight = preSum[n] - sumLeft;
            return Math.abs(sumLeft - sumRight);
        } else { // right == left
            long sumLeft = preSum[left];
            long sumRight = preSum[n] - sumLeft - nums[left];
            return Math.min(Math.abs(sumLeft + nums[left] - sumRight), Math.abs(sumLeft - sumRight - nums[left]));
        }
    }

}
