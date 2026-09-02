package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2541 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/2 10:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2541 {

    /**
     * @Description:
     * 给你两个整数数组 nums1 和 nums2 ，两个数组长度都是 n ，再给你一个整数 k 。
     * 你可以对数组 nums1 进行以下操作：
     * 选择两个下标 i 和 j ，将 nums1[i] 增加 k ，将 nums1[j] 减少 k 。
     * 换言之，nums1[i] = nums1[i] + k 且 nums1[j] = nums1[j] - k 。
     * 如果对于所有满足 0 <= i < n 都有 num1[i] == nums2[i] ，那么我们称 nums1 等于 nums2 。
     * 请你返回使 nums1 等于 nums2 的 最少 操作数。如果没办法让它们相等，请你返回 -1 。
     * tips:
     * n == nums1.length == nums2.length
     * 2 <= n <= 10^5
     * 0 <= nums1[i], nums2[j] <= 10^9
     * 0 <= k <= 10^5
     * @param: nums1
     * @param: nums2
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/09/02 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minOperations(int[] nums1, int[] nums2, int k) {
        long result;
        result = method_01(nums1, nums2, k);
        return result;
    }

    /**
     * @Description:
     * 1. 两个数组的总和是不变的, long totalAll = Arrays.stream(nums1).sum() + Arrays.stream(nums2).sum();
     * 2. 要使得两个数组相等, 那么他们的总和必须是相等的, 如果 totalAll % 2 != 0, 则无法使两个数组相等, 返回 -1.
     * 3. 假设 nums1[i] != nums2[i], 那么该如何操作使得 nums1[i] == nums2[i] 呢？ 由于只能 +/- k 所以, 如果
     * int sub = Math.abs(nums1[i] - nums2[i]), 如果 sub % k != 0, 则无法使 nums1[i] == nums2[i], 返回 -1.
     * 4. 既然需要找到最小值, 用动态规划来统计节点的状态数
     * 5. 为了使得结果最小, 在 nums1[i] 和 nums2[i] 之间取得平衡数, 并且当其相差奇数个 k 时, 可以调整是 +k / -k 都可以实现.
     * 6. long cnt 记录奇数的个数, long ans 记录总操作数
     * 7. 理解错误题目, 即操作只能在 nums1[] 数组上进行, nums2[] 数组不能进行操作
     * AC: 5ms/93.64MB
     * @param: nums1
     * @param: nums2
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/09/02 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        long total1 = 0;
        long total2 = 0;
        long increase = 0, decrease = 0; // 记录增加 k 的操作数, 减少 k 的操作数
        for (int i = 0; i < n; i++) {
            total1 += nums1[i];
            total2 += nums2[i];
            if (nums1[i] != nums2[i]) {
                int sub = Math.abs(nums1[i] - nums2[i]);
                if ((k == 0 && sub > 0) || sub % k != 0) {
                    return -1;
                }
                if (nums1[i] > nums2[i]) {
                    // 增加减少 k 的操作数
                    decrease += sub / k;
                } else {
                    increase += sub / k;
                }
            }
        }
        if (total1 != total2 || increase != decrease) {
            return -1;
        }

        return increase;
    }

}
