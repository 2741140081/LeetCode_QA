package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_330 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/17 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_330 {

    /**
     * @Description:
     * 给定一个已排序的正整数数组 nums ，和一个正整数 n 。
     * 从 [1, n] 区间内选取任意个数字补充到 nums 中，使得 [1, n] 区间内的任何数字都可以用 nums 中某几个数字的和来表示。
     * 请返回 满足上述要求的最少需要补充的数字个数 。
     *
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 10^4
     * nums 按 升序排列
     * 1 <= n <= 2^31 - 1
     * @param: nums
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/07/17 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minPatches(int[] nums, int n) {
        int result;
        result = method_01(nums, n);
        return result;
    }

    /**
     * @Description:
     * 1. 那些情况下需要向 nums 数组中添加数字, 当 sum < n 时, 需要添加数字, 或者 min(数组的最小值) > n 时, 需要添加数字
     * 2. 当 min > n 时, 需要添加数字, 至少需要添加多少一个数字, 能满足添加的数字任意组合可以满足 1 ~ n 的数, 并且可以重复添加同一个数字
     * 3. 假设 n = 6, 则必须要添加 1, 因为1是最小值, 且必须存在, 然后需要满足存在 2, 可以添加 1 或者 2来满足要求,
     * 4. 总结一下规律就是, 如果 n = 6, 可以使用 {1,2,4} 或者 {1,2,3} 两种方式来满足要求, n = 7 时, {1,2,4} 可以满足, {1,2,3,4},
     * 可就是说, {1，2，3，4} 可以优化为 {1,2,4}, 因为3 可以使用 1 + 2 来进行替代.
     * 5. 假设有 {1,2,3,4....n} => {1,2,[3],4,[5],[6],[7],8,[9],[10],[11],[12],[13],[14],[15],16} 然后就会得到一个结论,
     * 基于贪心的策略, 需要添加 2^x (x = [0 ~ k]) 的数, 例如2^0, 2^1 .. 2^k > n 时, 才能满足要求. 最多需要添加 k + 1 个数, 然后判断这些数是否在 nums[] 数组中,
     * 如果在, 则不需要添加, 否则需要添加.
     * 6. 判断是否存在的方法是 假设需要判断 2^5 是否存在于 nums[] 数组中, 则判断能否在 < 2^5 的数组中, 找到 x 个数, 这些数需要先删除{2^0 ~ 2^4} 一次,
     * 防止重复使用, 然后在查找是否有 j 个数, 满足 sum_j == 2^5
     * 7. 想到了贪心, 但是后续的思路存在问题, 即把添加的数固定成 2^x 的数, 导致后续无法处理. 题解的思路是
     * 当找不到某个数时, 添加一个数, 会使得范围扩大2倍, 然后在添加 nums[i], 由于 [1 ~ x] 都存在, 所以将 nums[i] 添加进来后, 新的范围是
     * [1 ~ x + nums[i]], i++, 然后重复这个过程, 直到 x > n
     * @param: nums
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/07/17 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int n) {
        int patches = 0;
        long x = 1;
        int length = nums.length, index = 0;
        while (x <= n) {
            if (index < length && nums[index] <= x) {
                x += nums[index];
                index++;
            } else {
                x *= 2;
                patches++;
            }
        }
        return patches;
    }

}
